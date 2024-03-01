package com.alibou.security.service;

import com.alibou.security.entity.MaterialType;
import com.alibou.security.entity.exception.RestException;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.repository.MaterialTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MaterialTypeService {

    MaterialTypeRepository materialTypeRepository;

    public ApiResponse<List<MaterialType>> getAll() {
        List<MaterialType> all = materialTypeRepository.findAll();
        return ApiResponse.successResponse(all);
    }

    public ApiResponse<MaterialType> getById(int id) {

        MaterialType materialType = materialTypeRepository.findById(id).orElseThrow(() -> RestException.restThrow("category not found"));

        return ApiResponse.successResponse(materialType);


    }

    public ApiResponse<?> insert(MaterialType itemType) {

        MaterialType itemType1 = new MaterialType();
        itemType1.setName(itemType.getName());
        MaterialType save = materialTypeRepository.save(itemType1);
        return ApiResponse.successResponse(save, "successfully inserted");

    }

    public ApiResponse<?> delete(int id) {
        materialTypeRepository.deleteById(id);
        return ApiResponse.successResponse("successfully deleted");

    }

    public ApiResponse<?> update(int id, MaterialType itemType) {
        MaterialType itemType1 = materialTypeRepository.findById(id).orElseThrow(() -> RestException.restThrow("category not found with this name"));
        itemType1.setName(itemType.getName());
        MaterialType save = materialTypeRepository.save(itemType1);
        return ApiResponse.successResponse(save, "successfully edited");


    }


}
