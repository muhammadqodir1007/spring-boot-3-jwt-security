package com.alibou.security.controller;

import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.dto.ItemDto;
import com.alibou.security.payload.response.ItemResponse;
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
    public ApiResponse<List<ItemResponse>> getAllItems() {
        return itemService.getAll();
    }

    @GetMapping("/search")
    public ApiResponse<List<ItemResponse>> search(@RequestParam String name) {
        return itemService.search(name);
    }

    @GetMapping("/category/{id}")
    public ApiResponse<List<ItemResponse>> getItemsByCategoryId(@PathVariable int id) {
        return itemService.getAllByCategory(id);
    }

    @GetMapping("/{id}")
    public ApiResponse<ItemResponse> getItemById(@PathVariable int id) {
        return itemService.getById(id);
    }

    @PostMapping
    public ApiResponse<?> addItem(@RequestBody ItemDto itemDto) {
        System.out.println("something ");
        return itemService.insert(itemDto);
    }


    @PatchMapping
    public ApiResponse<?> deleteItemById(@RequestBody ItemDto itemDto) {
        return itemService.delete(itemDto);
    }
}
