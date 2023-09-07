package com.example.orderservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserDetailsDto(Long id, String email, List<String> roles) {
}
