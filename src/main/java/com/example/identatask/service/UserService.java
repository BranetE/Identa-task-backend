package com.example.identatask.service;

import com.example.identatask.dto.AuthResponseDto;
import com.example.identatask.dto.CreateUserDto;
import com.example.identatask.dto.LoginDto;

public interface UserService {
    void createUser(CreateUserDto userDto);

    AuthResponseDto login(LoginDto loginDto);

    void deleteUser(Long id);
}
