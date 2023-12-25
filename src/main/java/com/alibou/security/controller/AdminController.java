package com.alibou.security.controller;

import com.alibou.security.payload.ApiResponse;
import com.alibou.security.service.AdminService;
import com.alibou.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;


    @GetMapping
    public ApiResponse<List<User>> getAllAdmins() {
        return adminService.getAll();
    }

    @GetMapping("/{username}")
    public ApiResponse<User> getAdminByUsername(@PathVariable String username) {
        return adminService.getByUsername(username);
    }

//    @PostMapping
//    public ApiResponse<User> addAdmin(@RequestBody User user) {
//        return adminService.insert(user);
//    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateAdmin(@PathVariable int id, @RequestBody User user) {
        return adminService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteAdmin(@PathVariable int id) {
        return adminService.delete(id);
    }
}
