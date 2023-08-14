package com.example.identatask.service;

import com.example.identatask.ModelUtils;
import com.example.identatask.dto.CreateOrderDto;
import com.example.identatask.dto.OrderDto;
import com.example.identatask.dto.mapper.OrderDtoMapper;
import com.example.identatask.model.Order;
import com.example.identatask.model.OrderStatus;
import com.example.identatask.model.User;
import com.example.identatask.repository.OrderRepository;
import com.example.identatask.repository.UserRepository;
import com.example.identatask.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrderTest() {
        CreateOrderDto createOrderDto = ModelUtils.getCreateOrderDto();
        User user = ModelUtils.getUser();
        Order order = ModelUtils.getOrder();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        doNothing().when(simpMessagingTemplate).convertAndSend("/order", OrderDtoMapper.toDto(order));

        orderService.createOrder(createOrderDto, 1L);

        verify(userRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(simpMessagingTemplate).convertAndSend("/order", OrderDtoMapper.toDto(order));
    }

    @Test
    void getOrdersByUserTest() {
        Order order = ModelUtils.getOrder();

        when(orderRepository.findOrdersByUserId(1L)).thenReturn(List.of(order));

        List<OrderDto> expected = List.of(OrderDtoMapper.toDto(order));

        assertEquals(expected, orderService.getOrdersByUserId(1L));
        verify(orderRepository).findOrdersByUserId(1L);
    }

    @Test
    void getAllOrdersTest() {
        Order order = ModelUtils.getOrder();

        when(orderRepository.findAllReverseSortedById()).thenReturn(List.of(order));

        List<OrderDto> expected = List.of(OrderDtoMapper.toDto(order));

        assertEquals(expected, orderService.getAll());
        verify(orderRepository).findAllReverseSortedById();
    }

    @Test
    void deleteOrderTest(){
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }

    @Test
    void changeOrderStatusTest(){
        doNothing().when(orderRepository).updateStatus(OrderStatus.ACCEPTED, 1L);

        orderService.changeStatus(1L, "ACCEPTED");

        verify(orderRepository).updateStatus(OrderStatus.ACCEPTED, 1L);
    }
}
