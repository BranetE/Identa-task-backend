package com.example.identatask.service.impl;

import com.example.identatask.dto.CreateOrderDto;
import com.example.identatask.dto.OrderDto;
import com.example.identatask.dto.mapper.OrderDtoMapper;
import com.example.identatask.model.Order;
import com.example.identatask.model.OrderStatus;
import com.example.identatask.model.User;
import com.example.identatask.repository.OrderRepository;
import com.example.identatask.repository.UserRepository;
import com.example.identatask.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void createOrder(CreateOrderDto orderDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        Order order = Order.builder()
                .name(orderDto.name())
                .status(OrderStatus.NEW)
                .comment(orderDto.comment())
                .price(orderDto.price())
                .user(user)
                .build();
        orderRepository.save(order);
        simpMessagingTemplate.convertAndSend("/order",OrderDtoMapper.toDto(order));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findOrdersByUserId(userId).stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderDto> getAll() {
        return orderRepository.findAllReverseSortedById().stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id, String orderStatus) {
        orderRepository.updateStatus(OrderStatus.valueOf(orderStatus.toUpperCase()), id);
    }
}
