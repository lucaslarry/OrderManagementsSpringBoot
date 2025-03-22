package com.course.springboot.repositories;

import com.course.springboot.entities.Order;
import com.course.springboot.entities.OrderItem;
import com.course.springboot.entities.Product;
import com.course.springboot.entities.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    default Optional<OrderItem> findByOrderAndProduct(Order order, Product product) {
        OrderItemPK id = new OrderItemPK();
        id.setOrder(order);
        id.setProduct(product);
        return this.findById(id);
    }
}