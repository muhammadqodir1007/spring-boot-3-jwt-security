package com.alibou.security.controller;

import com.alibou.security.entity.Item;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.ItemDto;
import com.alibou.security.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;


    @GetMapping
    public ApiResponse<List<Item>> getAllItems() {
        return itemService.getAll();
    }

    @GetMapping("/search")
    public ApiResponse<List<Item>> search(@RequestParam String name) {
        return itemService.search(name);
    }

    @GetMapping("/category/{id}")
    public ApiResponse<List<Item>> getItemsByCategoryId(@PathVariable int id) {
        return itemService.getAllByCategory(id);
    }

    @GetMapping("/{id}")
    public ApiResponse<Item> getItemById(@PathVariable int id) {
        return itemService.getById(id);
    }

    @PostMapping
    public ApiResponse<?> addItem(@RequestBody ItemDto itemDto) {
        System.out.println("something ");
        return itemService.insert(itemDto);
    }


    @DeleteMapping
    public ApiResponse<?> deleteItemById(@RequestBody ItemDto itemDto) {
        return itemService.delete(itemDto);
    }
}
