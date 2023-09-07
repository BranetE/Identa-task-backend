package com.example.orderservice.controller;

import com.example.orderservice.ModelUtils;
import com.example.orderservice.dto.CreateUserDto;
import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private final String path = "/api/auth";
    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @SneakyThrows
    void registerTest() {
        CreateUserDto createUserDto = ModelUtils.getCreateUserDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(createUserDto);

        mockMvc.perform(post(path + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());

        verify(userService).createUser(any(CreateUserDto.class));
    }

    @Test
    @SneakyThrows
    void loginTest() {
        LoginDto loginDto = ModelUtils.getLoginDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(post(path + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(userService).login(any(LoginDto.class));
    }

}
