package com.example.identatask.dto.mapper;

import com.example.identatask.dto.OrderDto;
import com.example.identatask.model.Order;

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
