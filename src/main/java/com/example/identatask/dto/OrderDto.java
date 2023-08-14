package com.example.identatask.dto;

import lombok.Builder;

@Builder
public record OrderDto(Long id, String name, String comment, String status, Long price) {
}
