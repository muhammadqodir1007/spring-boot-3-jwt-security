package com.alibou.security.service;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.Item;
import com.alibou.security.entity.ItemType;
import com.alibou.security.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.ItemDto;
import com.alibou.security.repository.CategoryRepository;
import com.alibou.security.repository.ItemRepository;
import com.alibou.security.repository.ItemTypeRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemTypeRepository itemTypeRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemTransactionService itemTransactionService;
    private final UserRepository userRepository;

    public ApiResponse<List<Item>> getAll() {
        return ApiResponse.successResponse(itemRepository.findAll());
    }

    public ApiResponse<Item> getById(int id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> RestException.restThrow("Product not found"));
        return ApiResponse.successResponse(item);
    }

    public ApiResponse<?> insert(ItemDto itemDto) {
        Category category = categoryRepository.findById(itemDto.getCategoryId()).orElseThrow(() -> RestException.restThrow("Category not found"));
        User user = userRepository.findById(itemDto.getAdminId()).orElseThrow(() -> RestException.restThrow("User not found"));
        ItemType itemType = itemTypeRepository.findById(itemDto.getItemType()).orElseThrow(() -> RestException.restThrow("Item Type not found"));


        boolean isExist = itemRepository.existsByItemType(itemType);
        Item item;
        if (isExist) {
            item = itemRepository.findByItemType(itemType).orElseThrow(() -> RestException.restThrow("item not found"));
            item.setQuantity(item.getQuantity() + itemDto.getQuantity());
//            itemRepository.save(item);
            item.setUpdatedAt(LocalDateTime.now());
        } else {
            item = new Item();
            item.setItemType(itemType);
            item.setCategory(category);
//            item.setUser(user);
            item.setDescription(itemDto.getDescription());
            item.setQuantity(itemDto.getQuantity());
            item.setCreatedAt(LocalDateTime.now());
//            itemRepository.save(item);
        }
        item.setUser(user);


        itemRepository.save(item);

        itemTransactionService.add(itemDto, "added");
        return ApiResponse.successResponse(item, "Material successfully saved");
    }

    public ApiResponse<List<Item>> getAllByCategory(int categoryId) {
        List<Item> allByCategoryId = itemRepository.findAllByCategoryId(categoryId);
        return ApiResponse.successResponse(allByCategoryId);
    }

    public ApiResponse<?> delete(ItemDto itemDto) {
        ItemType itemType1 = itemTypeRepository.findById(itemDto.getItemType()).orElseThrow(() -> RestException.restThrow("item type not found"));
        User user = userRepository.findById(itemDto.getAdminId()).orElseThrow(() -> RestException.restThrow("user not found"));
        boolean exists = itemRepository.existsByItemType(itemType1);
        Item save;
        if (exists) {
            Item item = itemRepository.findByItemType(itemType1).orElseThrow(() -> RestException.restThrow("not found"));
            if (item.getQuantity() < itemDto.getQuantity())
                throw RestException.restThrow("there is not enough product");
            item.setQuantity(item.getQuantity() - itemDto.getQuantity());
            item.setUser(user);
            save = itemRepository.save(item);

        } else {
            throw RestException.restThrow("item not found");
        }
        itemTransactionService.delete(itemDto, "olindi");


        return ApiResponse.successResponse(save);
    }
}
