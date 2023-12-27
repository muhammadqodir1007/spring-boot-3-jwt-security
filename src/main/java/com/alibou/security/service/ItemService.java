package com.alibou.security.service;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.Item;
import com.alibou.security.entity.ItemType;
import com.alibou.security.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.ItemDto;
import com.alibou.security.payload.ItemResponse;
import com.alibou.security.payload.UserDto;
import com.alibou.security.repository.CategoryRepository;
import com.alibou.security.repository.ItemRepository;
import com.alibou.security.repository.ItemTypeRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemTypeRepository itemTypeRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemTransactionService itemTransactionService;
    private final UserRepository userRepository;

    public ApiResponse<List<Item>> search(String name) {
        ItemType itemType = itemTypeRepository.findByName(name).orElseThrow(() -> RestException.restThrow("item not found "));
        List<Item> items = itemRepository.searchAllByItemType(itemType);
        return ApiResponse.successResponse(items);
    }

    public ApiResponse<List<ItemResponse>> getAll() {

        List<Item> items = itemRepository.findAll();
        List<ItemResponse> list = new ArrayList<>();
        for (Item item : items) {

            UserDto userDto = new UserDto();
            userDto.setRole(item.getUser().getRole().name());
            userDto.setId(item.getUser().getId());
            userDto.setUsername(item.getUser().getUsername());

            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setItemType(item.getItemType());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setCategory(item.getCategory());
            itemResponse.setId(item.getId());
            itemResponse.setDescription(item.getDescription());
            itemResponse.setUserDto(userDto);
            itemResponse.setCreatedAt(item.getCreatedAt());
            itemResponse.setUpdatedAt(item.getUpdatedAt());
            list.add(itemResponse);

        }


        return ApiResponse.successResponse(list);


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
            item.setUpdatedAt(LocalDateTime.now());
        } else {
            item = new Item();
            item.setItemType(itemType);
            item.setCategory(category);
            item.setDescription(itemDto.getDescription());
            item.setQuantity(itemDto.getQuantity());
            item.setCreatedAt(LocalDateTime.now());
        }
        item.setUser(user);
        Item save = itemRepository.save(item);
        ItemResponse itemResponse = mapItemToResponse(save);
        itemTransactionService.add(itemDto, "added");
        return ApiResponse.successResponse(itemResponse, "Material successfully saved");
    }

    public ApiResponse<List<ItemResponse>> getAllByCategory(int categoryId) {
        List<Item> allByCategoryId = itemRepository.findAllByCategoryId(categoryId);
        List<ItemResponse> list = new ArrayList<>();
        for (Item item : allByCategoryId) {
            ItemResponse toResponse = mapItemToResponse(item);
            list.add(toResponse);
        }
        return ApiResponse.successResponse(list);
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

    private ItemResponse mapItemToResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setItemType(item.getItemType());
        itemResponse.setQuantity(item.getQuantity());
        itemResponse.setCategory(item.getCategory());
        itemResponse.setId(item.getId());
        itemResponse.setDescription(item.getDescription());

        // Check if user is not null before mapping
        if (item.getUser() != null) {
            itemResponse.setUserDto(mapUserToDto(item.getUser()));
        }

        itemResponse.setCreatedAt(item.getCreatedAt());
        itemResponse.setUpdatedAt(item.getUpdatedAt());
        return itemResponse;
    }

}
