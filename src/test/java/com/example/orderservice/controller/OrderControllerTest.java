package com.example.orderservice.controller;

import com.example.orderservice.ModelUtils;
import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.model.User;
import com.example.orderservice.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private final String path = "/api/order";
    private MockMvc mockMvc;

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private User user;

    @InjectMocks
    OrderController orderController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    @SneakyThrows
    @WithAnonymousUser
    void createOrderTest() {
        CreateOrderDto createOrderDto = ModelUtils.getCreateOrderDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(createOrderDto);

        this.user = ModelUtils.getUser();

//        when(user.getId()).thenReturn(1L);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        verify(orderService).createOrder(any(CreateOrderDto.class), any(Long.class));
    }
}
