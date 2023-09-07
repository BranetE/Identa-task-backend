package com.example.orderservice.dto;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {
    String email;
    String password;
}
