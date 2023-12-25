package com.alibou.security.controller;

import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.service.MaterialCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-category")
public class MaterialCategoryController {

    private final MaterialCategoryService materialCategoryService;

    public MaterialCategoryController(MaterialCategoryService materialCategoryService) {
        this.materialCategoryService = materialCategoryService;
    }

    @GetMapping
    public ApiResponse<List<MaterialCategory>> getAllMaterialCategories() {
        return materialCategoryService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<MaterialCategory> getMaterialCategoryById(@PathVariable int id) {
        return materialCategoryService.getById(id);
    }

    @PostMapping
    public ApiResponse<?> addMaterialCategory(@RequestBody MaterialCategory materialCategory) {
        return materialCategoryService.insert(materialCategory);
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> updateMaterialCategory(@PathVariable int id, @RequestBody MaterialCategory materialCategory) {
        return materialCategoryService.update(id, materialCategory);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteMaterialCategory(@PathVariable int id) {
        return materialCategoryService.delete(id);
    }
}
