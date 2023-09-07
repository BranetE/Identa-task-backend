package com.example.orderservice.dto.mapper;

import com.example.orderservice.dto.UserDetailsDto;
import com.example.orderservice.security.CustomUserDetails;

public class UserDetailsDtoMapper {

    public static UserDetailsDto convertToDto(CustomUserDetails userDetails){
        return UserDetailsDto.builder()
                .id(userDetails.getUser().getId())
                .email(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(Object::toString)
                        .toList())
                .build();
    }
}
