package com.course.springboot.config;

import com.course.springboot.entities.Category;
import com.course.springboot.entities.Order;
import com.course.springboot.entities.enums.OrderStatus;
import com.course.springboot.repositories.CategoryRepository;
import com.course.springboot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;
import com.course.springboot.entities.User;
import com.course.springboot.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public void run(String... args) throws Exception {
        Category cat1= new Category(null, "Electronics");
        Category cat2= new Category(null, "Books");
        Category cat3= new Category(null, "Computers");

        User u1 = new User(null, "Maria", "maria@gmail.com", "988888888","senha");
        User u2 = new User(null, "Joao", "joao@gmail.com", "977777777","ahnes");

        Order o1 = new Order(null, Instant.parse("2024-08-17T12:14:50Z"), OrderStatus.PAID,u1);
        Order o2 = new Order(null, Instant.parse("2024-09-11T14:44:23Z"),OrderStatus.WAITING_PAYMENT,u2);
        Order o3 = new Order(null, Instant.parse("2024-09-17T13:11:43Z"),OrderStatus.WAITING_PAYMENT,u1);

        userRepository.saveAll(Arrays.asList(u1,u2));
        orderRepository.saveAll(Arrays.asList(o1,o2,o3));
        categoryRepository.saveAll(Arrays.asList(cat1,cat2,cat3));
    }
    

}
