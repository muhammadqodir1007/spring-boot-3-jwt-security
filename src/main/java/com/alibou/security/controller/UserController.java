package com.alibou.security.controller;

import com.alibou.security.auth.AuthenticationResponse;
import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.payload.ApiResponse;
import com.alibou.security.payload.UserDto;
import com.alibou.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse<List<UserDto>> list = userService.getAllUsers();
        return ResponseEntity.ok(list);

    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody RegisterRequest request) {
        AuthenticationResponse insert = userService.insert(request);
        return ResponseEntity.ok(insert);
    }


//    @PatchMapping
//    public ResponseEntity<?> changePassword(
//            @RequestBody ChangePasswordRequest request,
//            Principal connectedUser
//    ) {
//        service.changePassword(request, connectedUser);
//        return ResponseEntity.ok().build();
//    }
}
