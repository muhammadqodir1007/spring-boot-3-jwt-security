package com.alibou.security.service;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ApiResponse<List<Category>> getAll() {
        List<Category> allCategories = categoryRepository.findAll();
        return ApiResponse.successResponse(allCategories);
    }

    public ApiResponse<?> insert(Category category) {
        categoryRepository.save(category);
        return ApiResponse.successResponse("inserted successfully");
    }

    public ApiResponse<Category> getById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("category not found"));
        return ApiResponse.successResponse(category);
    }

    public ApiResponse<Category> edit(int id, Category updatedCategory) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("category not found"));
        categoryToUpdate.setName(updatedCategory.getName());
        Category savedCategory = categoryRepository.save(categoryToUpdate);
        return ApiResponse.successResponse(savedCategory, "updated");
    }
}
