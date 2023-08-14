package com.example.identatask.dto;

import lombok.Builder;

@Builder
public record CreateUserDto(String email, String firstName, String lastName, String password) {
}
