package com.example.identatask.dto.mapper;

import com.example.identatask.dto.UserDetailsDto;
import com.example.identatask.security.CustomUserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsDtoMapper {

    public static UserDetailsDto convertToDto(CustomUserDetails userDetails){
        return UserDetailsDto.builder()
                .id(userDetails.getId())
                .email(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(Object::toString)
                        .toList())
                .build();
    }
}
