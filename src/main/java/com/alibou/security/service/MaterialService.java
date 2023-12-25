package com.alibou.security.service;

import com.alibou.security.entity.Material;
import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.entity.MaterialType;
import com.alibou.security.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.MaterialDto;
import com.alibou.security.repository.MaterialCategoryRepository;
import com.alibou.security.repository.MaterialRepository;
import com.alibou.security.repository.MaterialTypeRepository;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialTypeRepository materialTypeRepository;
    MaterialCategoryRepository materialCategoryRepository;
    private final MaterialTransactionService materialTransactionService;
    private final UserRepository userRepository;

    public ApiResponse<List<Material>> getAll() {
        List<Material> allMaterials = materialRepository.findAll();
        return ApiResponse.successResponse(allMaterials);
    }

    public ApiResponse<Material> getById(int id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> RestException.restThrow("Material not found"));
        return ApiResponse.successResponse(material);
    }

    public ApiResponse<?> insert(MaterialDto materialDto) {
        MaterialCategory category = materialCategoryRepository.findById(materialDto.getCategoryId()).orElseThrow(() -> RestException.restThrow("Category not found"));
        User user = userRepository.findById(materialDto.getAdminId()).orElseThrow(() -> RestException.restThrow("User not found"));
        MaterialType itemType = materialTypeRepository.findById(materialDto.getMaterialTypeId()).orElseThrow(() -> RestException.restThrow("Item Type not found"));


        boolean isExist = materialRepository.existsByMaterialType(itemType);
        Material item;
        if (isExist) {
            item = materialRepository.findByMaterialType(itemType).orElseThrow(() -> RestException.restThrow("item not found"));
            item.setQuantity(item.getQuantity() + materialDto.getQuantity());
//            itemRepository.save(item);
            item.setUpdatedAt(LocalDateTime.now());
        } else {
            item = new Material();
            item.setMaterialType(itemType);
            item.setMaterialCategory(category);
            item.setDescription(materialDto.getDescription());
            item.setQuantity(materialDto.getQuantity());
            item.setCreatedAt(LocalDateTime.now());
        }
        item.setUser(user);


        materialRepository.save(item);

        materialTransactionService.add(materialDto, "added");
        return ApiResponse.successResponse(item, "Material successfully saved");
    }


    public ApiResponse<List<Material>> getAllByCategoryId(int id) {
        List<Material> all = materialRepository.findAllByMaterialCategoryId(id);
        return ApiResponse.successResponse(all);


    }

    public ApiResponse<?> delete(MaterialDto materialDto) {

        MaterialType itemType1 = materialTypeRepository.findById(materialDto.getMaterialTypeId()).orElseThrow(() -> RestException.restThrow("item type not found"));
        User user = userRepository.findById(materialDto.getAdminId()).orElseThrow(() -> RestException.restThrow("user not found"));
        boolean exists = materialRepository.existsByMaterialType(itemType1);
        Material save;
        if (exists) {
            Material item = materialRepository.findByMaterialType(itemType1).orElseThrow(() -> RestException.restThrow("not found"));
            if (item.getQuantity() < materialDto.getQuantity())
                throw RestException.restThrow("there is not enough product");
            item.setQuantity(item.getQuantity() - materialDto.getQuantity());
            item.setUser(user);
            save = materialRepository.save(item);

        } else {
            throw RestException.restThrow("item not found");
        }
        materialTransactionService.delete(materialDto, "olindi");


        return ApiResponse.successResponse(save);
    }
}

