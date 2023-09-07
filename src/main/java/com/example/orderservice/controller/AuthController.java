package com.example.orderservice.controller;

import com.example.orderservice.dto.AuthResponseDto;
import com.example.orderservice.dto.CreateUserDto;
import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserDto registerDto){
        userService.createUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User is registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginDto));
    }
}
