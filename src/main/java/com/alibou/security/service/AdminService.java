//package com.alibou.security.service;
//
//import com.alibou.security.exception.RestException;
//import com.alibou.security.payload.ApiResponse;
//import com.alibou.security.repository.UserRepository;
//import com.alibou.security.user.Role;
//import com.alibou.security.user.User;
//import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class AdminService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public ApiResponse<List<User>> getAll() {
//        return ApiResponse.successResponse(userRepository.findAll());
//    }
//
//    public ApiResponse<User> getByUsername(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> RestException.restThrow("username not found"));
//        return ApiResponse.successResponse(user);
//    }
//
//    public ApiResponse<User> insert(User user) {
//        User newUser = new User();
//        newUser.setRole(Role.ADMIN);
//        newUser.setUsername(user.getUsername());
//        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(newUser);
//        return ApiResponse.successResponse("created successfully");
//    }
//
//    public ApiResponse<?> delete(int id) {
//        userRepository.deleteById(id);
//        return ApiResponse.successResponse("deleted");
//    }
//
//    public ApiResponse<User> update(int id, User updatedUser) {
//        User user = userRepository.findById(id).orElseThrow(() -> RestException.restThrow("user not found"));
//        user.setRole(updatedUser.getRole());
//        user.setPassword(updatedUser.getPassword());
//        user.setUsername(updatedUser.getUsername());
//        userRepository.save(user);
//        return ApiResponse.successResponse("edited successfully");
//    }
//}
