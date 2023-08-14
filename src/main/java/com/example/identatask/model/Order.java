package com.example.identatask.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @Column
    private String comment;

    @Column
    private Long price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
