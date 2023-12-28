package com.alibou.security.service;

import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.entity.MaterialTransaction;
import com.alibou.security.entity.MaterialType;
import com.alibou.security.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.dto.MaterialDto;
import com.alibou.security.repository.MaterialCategoryRepository;
import com.alibou.security.repository.MaterialTransactionRepository;
import com.alibou.security.repository.MaterialTypeRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MaterialTransactionService {
    private final MaterialTransactionRepository materialTransactionRepository;

    private final MaterialTypeRepository materialTypeRepository;
    private final MaterialCategoryRepository materialCategoryRepository;
    private final UserRepository userRepository;

    public void add(MaterialDto item, String actionType) {
        MaterialCategory category = materialCategoryRepository.findById(item.getCategoryId()).orElseThrow(() -> RestException.restThrow("Category not found"));
        User user = userRepository.findById(item.getAdminId()).orElseThrow(() -> RestException.restThrow("User not found"));
        MaterialType itemType = materialTypeRepository.findById(item.getMaterialTypeId()).orElseThrow(() -> RestException.restThrow("Item Type not found"));


        MaterialTransaction itemTransaction = new MaterialTransaction();
        itemTransaction.setItemType(itemType);
        itemTransaction.setCategory(category);
        itemTransaction.setUser(user);
        itemTransaction.setQuantity(item.getQuantity());
        itemTransaction.setActionType(actionType);
        itemTransaction.setActionDate(LocalDateTime.now());
        materialTransactionRepository.save(itemTransaction);
    }

    public ApiResponse<MaterialTransaction> getById(int id) {
        MaterialTransaction materialTransaction = materialTransactionRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Transaction not found with this ID"));
        return ApiResponse.successResponse(materialTransaction);
    }

    public ApiResponse<List<MaterialTransaction>> getByAdminId(int adminId) {
        List<MaterialTransaction> allByAdminId = materialTransactionRepository.findAllByUserId(adminId);
        return ApiResponse.successResponse(allByAdminId);
    }

    public ApiResponse<List<MaterialTransaction>> getAll() {
        List<MaterialTransaction> all = materialTransactionRepository.findAll();
        return ApiResponse.successResponse(all);
    }

    public void delete(MaterialDto materialDto, String action) {
        MaterialCategory materialCategory = materialCategoryRepository.findById(materialDto.getCategoryId()).orElseThrow(() -> RestException.restThrow("category not found"));
        User user = userRepository.findById(materialDto.getAdminId()).orElseThrow(() -> RestException.restThrow("user not found"));
        MaterialType itemType = materialTypeRepository.findById(materialDto.getMaterialTypeId()).orElseThrow(() -> RestException.restThrow("item type not found"));


        MaterialTransaction itemTransaction = new MaterialTransaction();
        itemTransaction.setItemType(itemType);
        itemTransaction.setCategory(materialCategory);
        itemTransaction.setUser(user);
        itemTransaction.setQuantity(materialDto.getQuantity());
        itemTransaction.setActionDate(LocalDateTime.now());
        itemTransaction.setActionType(action);
        materialTransactionRepository.save(itemTransaction);
    }
}
