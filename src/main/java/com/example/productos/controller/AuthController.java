package com.example.productos.controller;

import com.example.productos.dto.LoginRequest;
import com.example.productos.dto.LoginResponse;
import com.example.productos.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/test")
    public String test() {
        return "Auth controller is working";
    }
}