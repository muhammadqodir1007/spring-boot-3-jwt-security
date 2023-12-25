package com.alibou.security.service;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemTransaction;
import com.alibou.security.entity.ItemType;
import com.alibou.security.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.ItemDto;
import com.alibou.security.repository.CategoryRepository;
import com.alibou.security.repository.ItemTransactionRepository;
import com.alibou.security.repository.ItemTypeRepository;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemTransactionService {

    private final ItemTransactionRepository itemTransactionRepository;
    private final CategoryRepository categoryRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final UserRepository userRepository;

    public void add(ItemDto item, String actionType) {
        Category category = categoryRepository.findById(item.getCategoryId()).orElseThrow(() -> RestException.restThrow("Category not found"));
        User user = userRepository.findById(item.getAdminId()).orElseThrow(() -> RestException.restThrow("User not found"));
        ItemType itemType = itemTypeRepository.findById(item.getItemType()).orElseThrow(() -> RestException.restThrow("Item Type not found"));


        ItemTransaction itemTransaction = new ItemTransaction();
        itemTransaction.setItemType(itemType);
        itemTransaction.setCategory(category);
        itemTransaction.setUser(user);
        itemTransaction.setQuantity(item.getQuantity());
        itemTransaction.setActionType(actionType);
        itemTransaction.setActionDate(LocalDateTime.now());
        itemTransactionRepository.save(itemTransaction);
    }

    public ApiResponse<ItemTransaction> getById(int id) {
        ItemTransaction itemTransaction = itemTransactionRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Transaction not found with this ID"));
        return ApiResponse.successResponse(itemTransaction);
    }

    public ApiResponse<List<?>> getByAdminId(int adminId) {
        List<ItemTransaction> allByAdminId = itemTransactionRepository.findAllByUserId(adminId);
        return ApiResponse.successResponse(allByAdminId);
    }

    public ApiResponse<List<ItemTransaction>> getAll() {
        List<ItemTransaction> allTransactions = itemTransactionRepository.findAll();
        return ApiResponse.successResponse(allTransactions);
    }

    public ApiResponse<?> delete(ItemDto item, String actionType) {

        Category category = categoryRepository.findById(item.getCategoryId()).orElseThrow(() -> RestException.restThrow("category not found"));
        User user = userRepository.findById(item.getAdminId()).orElseThrow(() -> RestException.restThrow("user not found"));
        ItemType itemType = itemTypeRepository.findById(item.getItemType()).orElseThrow(() -> RestException.restThrow("item type not found"));


        ItemTransaction itemTransaction = new ItemTransaction();
        itemTransaction.setItemType(itemType);
        itemTransaction.setCategory(category);
        itemTransaction.setUser(user);
        itemTransaction.setQuantity(item.getQuantity());
        itemTransaction.setActionDate(LocalDateTime.now());
        itemTransaction.setActionType(actionType);
        itemTransactionRepository.save(itemTransaction);
        return ApiResponse.successResponse("Deleted successfully");
    }
}
