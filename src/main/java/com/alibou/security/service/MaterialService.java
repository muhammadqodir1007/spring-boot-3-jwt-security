package com.alibou.security.service;

import com.alibou.security.entity.Material;
import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.entity.MaterialType;
import com.alibou.security.entity.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.dto.MaterialDto;
import com.alibou.security.payload.dto.UserDto;
import com.alibou.security.payload.response.MaterialResponse;
import com.alibou.security.repository.MaterialCategoryRepository;
import com.alibou.security.repository.MaterialRepository;
import com.alibou.security.repository.MaterialTypeRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialTypeRepository materialTypeRepository;
    MaterialCategoryRepository materialCategoryRepository;
    private final MaterialTransactionService materialTransactionService;
    private final UserRepository userRepository;

    public ApiResponse<List<MaterialResponse>> getAll() {
        List<Material> list = materialRepository.findAll();
        List<MaterialResponse> collect = list.stream().map(this::mapItemToResponse).toList();
        return ApiResponse.successResponse(collect);
    }

    public ApiResponse<List<MaterialResponse>> search(String name) {
        List<Material> materials = materialRepository.findByMaterialTypeNameContaining(name);
        List<MaterialResponse> responses = materials.stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());
        return ApiResponse.successResponse(responses);
    }


    public ApiResponse<MaterialResponse> getById(int id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> RestException.restThrow("Material not found"));
        return ApiResponse.successResponse(mapItemToResponse(material));
    }

    public ApiResponse<?> insert(MaterialDto materialDto) {
        MaterialCategory category = materialCategoryRepository.findById(materialDto.getCategoryId()).orElseThrow(() -> RestException.restThrow("Category not found"));
        User user = userRepository.findById(materialDto.getAdminId()).orElseThrow(() -> RestException.restThrow("User not found"));
        MaterialType itemType = materialTypeRepository.findById(materialDto.getMaterialTypeId()).orElseThrow(() -> RestException.restThrow("Material Type not found"));
        boolean isExist = materialRepository.existsByMaterialType(itemType);
        Material material;
        if (isExist) {
            material = materialRepository.findByMaterialType(itemType).orElseThrow(() -> RestException.restThrow("material not found"));
            material.setQuantity(material.getQuantity() + materialDto.getQuantity());
//            itemRepository.save(material);
            material.setUpdatedAt(LocalDateTime.now());
        } else {
            material = new Material();
            material.setMaterialType(itemType);
            material.setMaterialCategory(category);
            material.setDescription(materialDto.getDescription());
            material.setQuantity(materialDto.getQuantity());
            material.setCreatedAt(LocalDateTime.now());
        }
        material.setUser(user);

        materialRepository.save(material);
        materialTransactionService.add(materialDto, "added");
        return ApiResponse.successResponse(mapItemToResponse(material), "Material successfully saved");
    }


    public ApiResponse<List<MaterialResponse>> getAllByCategoryId(int id) {
        List<Material> all = materialRepository.findAllByMaterialCategoryId(id);
        List<MaterialResponse> list = all.stream().map(this::mapItemToResponse).toList();
        return ApiResponse.successResponse(list);
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


        return ApiResponse.successResponse(mapItemToResponse(save));
    }


    private UserDto mapUserToDto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setRole(user.getRole().name());
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            return userDto;
        }
        // Handle the case when the user is null
        return null; // Or create an empty UserDto object based on your logic
    }

    private MaterialResponse mapItemToResponse(Material material) {
        MaterialResponse materialResponse = new MaterialResponse();
        materialResponse.setMaterialType(material.getMaterialType());
        materialResponse.setQuantity(material.getQuantity());
        materialResponse.setCategory(material.getMaterialCategory());
        materialResponse.setId(material.getId());
        materialResponse.setDescription(material.getDescription());

        // Check if user is not null before mapping
        if (material.getUser() != null) {
            materialResponse.setUserDto(mapUserToDto(material.getUser()));
        }

        materialResponse.setCreatedAt(material.getCreatedAt());
        materialResponse.setUpdatedAt(material.getUpdatedAt());
        return materialResponse;
    }


}

