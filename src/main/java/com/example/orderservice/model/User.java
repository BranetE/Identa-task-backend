package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column
    String email;

    @Column
    String password;

    @Column(name="first_name")
    String firstName;

    @Column(name="last_name")
    String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Order> orders;
}
