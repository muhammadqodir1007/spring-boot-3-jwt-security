package com.alibou.security.service;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.Item;
import com.alibou.security.entity.ItemType;
import com.alibou.security.entity.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.dto.ItemDto;
import com.alibou.security.payload.dto.UserDto;
import com.alibou.security.payload.response.ItemResponse;
import com.alibou.security.repository.CategoryRepository;
import com.alibou.security.repository.ItemRepository;
import com.alibou.security.repository.ItemTypeRepository;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemTypeRepository itemTypeRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemTransactionService itemTransactionService;
    private final UserRepository userRepository;

    public ApiResponse<List<ItemResponse>> search(String name) {
        List<Item> items = itemRepository.findByItemTypeNameContaining(name);
        List<ItemResponse> responses = items.stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());
        return ApiResponse.successResponse(responses);
    }


    public ApiResponse<List<ItemResponse>> getAll() {
        List<Item> items = itemRepository.findAll();
        List<ItemResponse> list = items.stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());

        return ApiResponse.successResponse(list);
    }


    public ApiResponse<ItemResponse> getById(int id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("Product not found"));

        return ApiResponse.successResponse(mapItemToResponse(item));
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
        List<Item> itemsByCategoryId = itemRepository.findAllByCategoryId(categoryId);
        List<ItemResponse> itemResponses = itemsByCategoryId.stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());

        return ApiResponse.successResponse(itemResponses);
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
        return null;
    }

    private ItemResponse mapItemToResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setItemType(item.getItemType());
        itemResponse.setQuantity(item.getQuantity());
        itemResponse.setCategory(item.getCategory());
        itemResponse.setId(item.getId());
        itemResponse.setDescription(item.getDescription());

        if (item.getUser() != null) {
            itemResponse.setUserDto(mapUserToDto(item.getUser()));
        }

        itemResponse.setCreatedAt(item.getCreatedAt());
        itemResponse.setUpdatedAt(item.getUpdatedAt());
        return itemResponse;
    }

}
