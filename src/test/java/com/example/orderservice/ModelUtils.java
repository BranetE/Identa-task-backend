package com.example.orderservice;

import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.dto.CreateUserDto;
import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.model.Role;
import com.example.orderservice.model.User;

public class ModelUtils {
    public static User getUser() {
        return User.builder()
                .id(1L)
                .email("email@test.com")
                .firstName("Test User")
                .lastName("Test User")
                .password("userpass")
                .role(Role.USER)
                .build();
    }

    public static CreateUserDto getCreateUserDto() {
        return CreateUserDto.builder()
                .email("email@test.com")
                .firstName("Test User")
                .lastName("Test User")
                .password("userpass")
                .build();
    }

    public static LoginDto getLoginDto() {
        return LoginDto.builder()
                .email("email@test.com")
                .password("userpass")
                .build();
    }

    public static CreateOrderDto getCreateOrderDto() {
         return CreateOrderDto.builder()
                .name("Test Order")
                .comment("Test Comment")
                .price(100L)
                .build();
    }

    public static Order getOrder() {
        return Order.builder()
                .name("Test Order")
                .status(OrderStatus.NEW)
                .comment("Test Comment")
                .price(100L)
                .build();
    }
}
