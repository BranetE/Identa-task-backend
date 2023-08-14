package com.example.identatask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private String tokenType;
    private UserDetailsDto user;
}
