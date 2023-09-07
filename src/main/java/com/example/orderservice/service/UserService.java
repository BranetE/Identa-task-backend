package com.example.orderservice.service;

import com.example.orderservice.dto.AuthResponseDto;
import com.example.orderservice.dto.CreateUserDto;
import com.example.orderservice.dto.LoginDto;

public interface UserService {
    void createUser(CreateUserDto userDto);

    AuthResponseDto login(LoginDto loginDto);

    void deleteUser(Long id);
}
