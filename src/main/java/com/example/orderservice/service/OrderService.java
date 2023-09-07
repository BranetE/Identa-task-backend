package com.example.orderservice.service;

import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.dto.OrderDto;

import java.util.List;

public interface OrderService {
    void createOrder(CreateOrderDto orderDto, Long userId);

    void deleteOrder(Long id);

    List<OrderDto> getOrdersByUserId(Long userId);

    void changeStatus(Long id, String orderStatus);

    List<OrderDto> getAll();
}
