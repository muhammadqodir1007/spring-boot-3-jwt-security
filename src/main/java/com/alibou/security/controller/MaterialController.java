package com.alibou.security.controller;

import com.alibou.security.entity.Material;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.dto.MaterialDto;
import com.alibou.security.service.MaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ApiResponse<List<Material>> getAllMaterials() {
        return materialService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<Material> getMaterialById(@PathVariable int id) {
        return materialService.getById(id);
    }

    @PostMapping
    public ApiResponse<?> addMaterial(@RequestBody MaterialDto materialDto) {
        return materialService.insert(materialDto);
    }

    @GetMapping("/category/{id}")
    public ApiResponse<List<Material>> getAllByCategoryId(@PathVariable int id) {
        return materialService.getAllByCategoryId(id);


    }


    @DeleteMapping
    public ApiResponse<?> deleteMaterialById(@RequestBody MaterialDto materialDto) {
        return materialService.delete(materialDto);
    }
}
