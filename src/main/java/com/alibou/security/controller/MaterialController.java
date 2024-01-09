package com.alibou.security.controller;

import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.dto.MaterialDto;
import com.alibou.security.payload.response.MaterialResponse;
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
    public ApiResponse<List<MaterialResponse>> getAllMaterials() {
        return materialService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<MaterialResponse> getMaterialById(@PathVariable int id) {
        return materialService.getById(id);
    }

    @PostMapping
    public ApiResponse<?> addMaterial(@RequestBody MaterialDto materialDto) {
        return materialService.insert(materialDto);
    }

    @GetMapping("/category/{id}")
    public ApiResponse<List<MaterialResponse>> getAllByCategoryId(@PathVariable int id) {
        return materialService.getAllByCategoryId(id);
    }

    @GetMapping("/search")
    public ApiResponse<List<MaterialResponse>> search(@RequestParam String name) {
        return materialService.search(name);
    }


    @PatchMapping
    public ApiResponse<?> deleteMaterialById(@RequestBody MaterialDto materialDto) {
        return materialService.delete(materialDto);
    }
}
