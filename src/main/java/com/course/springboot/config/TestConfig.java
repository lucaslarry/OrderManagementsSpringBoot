package com.course.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.Arrays;
import com.course.springboot.entities.User;
import com.course.springboot.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "Maria", "maria@gmail.com", "988888888","senha");
        User u2 = new User(null, "Joao", "joao@gmail.com", "977777777","ahnes");
        userRepository.saveAll(Arrays.asList(u1,u2));
    }
    

}
