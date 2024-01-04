package com.alibou.security.controller;

import com.alibou.security.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    HomeService homeService;


    @GetMapping
    public Map<String, Long> count() {
        return homeService.count();


    }
}
