package com.alibou.security.controller;

import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.response.MaterialTransactionRes;
import com.alibou.security.service.MaterialTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/material-transactions")
public class MaterialTransactionController {

    private final MaterialTransactionService materialTransactionService;

    public MaterialTransactionController(MaterialTransactionService materialTransactionService) {
        this.materialTransactionService = materialTransactionService;
    }

    @GetMapping
    public ApiResponse<List<MaterialTransactionRes>> getAllMaterialTransactions() {
        return materialTransactionService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<MaterialTransactionRes> getMaterialTransactionById(@PathVariable int id) {
        return materialTransactionService.getById(id);
    }

    @GetMapping("/admin/{id}")
    public ApiResponse<List<MaterialTransactionRes>> getMaterialTransactionsByAdminId(@PathVariable int id) {
        return materialTransactionService.getByAdminId(id);
    }

}
