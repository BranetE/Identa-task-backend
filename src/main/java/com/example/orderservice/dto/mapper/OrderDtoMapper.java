package com.example.orderservice.dto.mapper;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.model.Order;

public class OrderDtoMapper{
    public static OrderDto toDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .name(order.getName())
                .status(order.getStatus().toString())
                .comment(order.getComment())
                .price(order.getPrice())
                .build();
    }
}
