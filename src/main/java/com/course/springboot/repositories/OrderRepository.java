package com.course.springboot.repositories;

import com.course.springboot.entities.Order;
import com.course.springboot.entities.Product;
import com.course.springboot.entities.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{


}
