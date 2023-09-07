package com.example.orderservice.repository;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from Order o order by o.id desc")
    List<Order> findAllReverseSortedById();

    @Query("from Order o where o.user.id = :userId order by o.status")
    List<Order> findOrdersByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update Order o set o.status = :orderStatus where o.id = :id")
    void updateStatus(@Param("orderStatus") OrderStatus status, @Param("id") Long id);
}
