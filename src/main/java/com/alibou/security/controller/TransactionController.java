package com.alibou.security.controller;

import com.alibou.security.entity.ItemTransaction;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.service.ItemTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {

//    this class belongs to item


    private final ItemTransactionService itemTransactionService;

    @GetMapping
    public ApiResponse<List<ItemTransaction>> getAllTransactions() {
        return itemTransactionService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getTransactionById(@PathVariable int id) {
        return itemTransactionService.getById(id);
    }

    @GetMapping("/admin/{adminId}")
    public ApiResponse<List<?>> getTransactionsByAdminId(@PathVariable int adminId) {
        return itemTransactionService.getByAdminId(adminId);
    }
}
