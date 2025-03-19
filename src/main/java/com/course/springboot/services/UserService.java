package com.course.springboot.services;

import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.User;
import com.course.springboot.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll(){
        return  repository.findAll().stream().map(value -> objectMapper.convertValue(value, UserDTO.class)).toList();
    }

    public UserDTO findById(Long id){
        Optional<User> obj = repository.findById(id);
        if (obj.isEmpty()) {
            return null;
        }
        return obj.map(value -> objectMapper.convertValue(value, UserDTO.class)).orElse(null);
    }

    public UserDTO insert(UserDTO obj){
        User user = objectMapper.convertValue(obj, User.class);
        return objectMapper.convertValue(userRepository.save(user), UserDTO.class);
    }

    public void delete(Long id){
        Optional<User> obj = repository.findById(id);

    }
}
