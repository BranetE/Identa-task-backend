package com.example.orderservice.dto;

import lombok.Builder;

@Builder
public record CreateOrderDto(String name, String comment, Long price) {
}
