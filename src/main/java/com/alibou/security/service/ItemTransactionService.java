package com.alibou.security.service;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemTransaction;
import com.alibou.security.entity.ItemType;
import com.alibou.security.entity.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.dto.ItemDto;
import com.alibou.security.payload.dto.UserDto;
import com.alibou.security.payload.response.TransactionResponse;
import com.alibou.security.repository.CategoryRepository;
import com.alibou.security.repository.ItemTransactionRepository;
import com.alibou.security.repository.ItemTypeRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.user.User;
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


    private UserDto mapUserToDto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setRole(user.getRole().name());
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            return userDto;
        }
        return null;
    }

    private TransactionResponse mapItemTransactionToResponse(ItemTransaction itemTransaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(itemTransaction.getId());
        transactionResponse.setItemType(itemTransaction.getItemType());
        transactionResponse.setQuantity(itemTransaction.getQuantity());
        transactionResponse.setUserDto(mapUserToDto(itemTransaction.getUser()));
        transactionResponse.setCategory(itemTransaction.getCategory());
        transactionResponse.setActionType(itemTransaction.getActionType());
        transactionResponse.setActionDate(itemTransaction.getActionDate());
        return transactionResponse;
    }

    private ItemTransaction mapItemDtoToTransaction(ItemDto item, String actionType) {
        Category category = categoryRepository.findById(item.getCategoryId())
                .orElseThrow(() -> RestException.restThrow("Category not found"));
        User user = userRepository.findById(item.getAdminId())
                .orElseThrow(() -> RestException.restThrow("User not found"));
        ItemType itemType = itemTypeRepository.findById(item.getItemType())
                .orElseThrow(() -> RestException.restThrow("Item Type not found"));

        ItemTransaction itemTransaction = new ItemTransaction();
        itemTransaction.setItemType(itemType);
        itemTransaction.setCategory(category);
        itemTransaction.setUser(user);
        itemTransaction.setQuantity(item.getQuantity());
        itemTransaction.setActionType(actionType);
        itemTransaction.setActionDate(LocalDateTime.now());
        return itemTransaction;
    }

    public void add(ItemDto item, String actionType) {
        ItemTransaction itemTransaction = mapItemDtoToTransaction(item, actionType);
        itemTransactionRepository.save(itemTransaction);
    }

    public ApiResponse<TransactionResponse> getById(int id) {
        ItemTransaction itemTransaction = itemTransactionRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Transaction not found with this ID"));
        TransactionResponse transactionResponse = mapItemTransactionToResponse(itemTransaction);
        return ApiResponse.successResponse(transactionResponse);
    }

    public ApiResponse<List<?>> getByAdminId(int adminId) {
        List<ItemTransaction> list = itemTransactionRepository.findAllByUserId(adminId);
        List<TransactionResponse> collect = list.stream().map(this::mapItemTransactionToResponse).toList();
        return ApiResponse.successResponse(collect);
    }

    public ApiResponse<List<TransactionResponse>> getAll() {
        List<ItemTransaction> allTransactions = itemTransactionRepository.findAll();
        List<TransactionResponse> collect = allTransactions.stream().map(this::mapItemTransactionToResponse).toList();
        return ApiResponse.successResponse(collect);
    }

    public ApiResponse<?> delete(ItemDto item, String actionType) {
        ItemTransaction itemTransaction = mapItemDtoToTransaction(item, actionType);
        itemTransactionRepository.save(itemTransaction);
        return ApiResponse.successResponse("Deleted successfully");
    }
}
