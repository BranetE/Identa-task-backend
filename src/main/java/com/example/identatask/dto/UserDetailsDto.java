package com.example.identatask.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserDetailsDto(Long id, String email, List<String> roles) {
}
