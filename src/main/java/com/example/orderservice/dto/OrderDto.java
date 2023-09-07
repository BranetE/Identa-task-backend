package com.example.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderDto(Long id, String name, String comment, String status, Long price) {
}
