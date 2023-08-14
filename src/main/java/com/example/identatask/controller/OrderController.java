package com.example.identatask.controller;

import com.example.identatask.dto.CreateOrderDto;
import com.example.identatask.dto.OrderDto;
import com.example.identatask.security.CustomUserDetails;
import com.example.identatask.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAll(){
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/getByUser/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> createOrder(@RequestBody CreateOrderDto orderDto, @AuthenticationPrincipal CustomUserDetails user){
        orderService.createOrder(orderDto, user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> updateStatus(@PathVariable Long id, @RequestParam String status){
        orderService.changeStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
