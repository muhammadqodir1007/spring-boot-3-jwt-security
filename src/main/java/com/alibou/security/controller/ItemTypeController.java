package com.alibou.security.controller;

import com.alibou.security.entity.ItemType;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.service.ItemTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-type")
public class ItemTypeController {

    private final ItemTypeService itemTypeService;

    public ItemTypeController(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    @GetMapping
    public ApiResponse<List<ItemType>> getAllItemTypes() {
        return itemTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<ItemType> getItemTypeById(@PathVariable int id) {
        return itemTypeService.getById(id);
    }

    @PostMapping
    public ApiResponse<?> addItemType(@RequestBody ItemType itemType) {
        return itemTypeService.insert(itemType);
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> updateItemType(@PathVariable int id, @RequestBody ItemType itemType) {
        return itemTypeService.update(id, itemType);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteItemType(@PathVariable int id) {
        return itemTypeService.delete(id);
    }
}
