package com.example.identatask.service;

import com.example.identatask.dto.CreateOrderDto;
import com.example.identatask.dto.OrderDto;
import com.example.identatask.model.Order;
import com.example.identatask.model.OrderStatus;

import java.util.List;

public interface OrderService {
    void createOrder(CreateOrderDto orderDto, Long userId);

    void deleteOrder(Long id);

    List<OrderDto> getOrdersByUserId(Long userId);

    void changeStatus(Long id, String orderStatus);

    List<OrderDto> getAll();
}
