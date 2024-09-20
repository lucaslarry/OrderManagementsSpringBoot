package com.course.springboot.repositories;

import com.course.springboot.entities.Category;
import com.course.springboot.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

}
