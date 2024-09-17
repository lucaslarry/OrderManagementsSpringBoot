package com.course.springboot.services;

import com.course.springboot.entities.Order;
import com.course.springboot.repositories.OrderRepository;
import com.course.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll(){
        return  repository.findAll();
    }
    public Order findById(Long id){
        Optional<Order> obj = repository.findById(id);
        if (obj.isEmpty()) {
            return null;
        }
        return obj.get();
    }
}
