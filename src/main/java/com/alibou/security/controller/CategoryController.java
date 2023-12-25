package com.alibou.security.controller;

import com.alibou.security.entity.Category;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> getCategoryById(@PathVariable int id) {
        return categoryService.getById(id);
    }

    @PostMapping
    public ApiResponse<?> addCategory(@RequestBody Category category) {
        return categoryService.insert(category);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
        return categoryService.edit(id, category);
    }
}
