package com.course.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.springboot.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    UserDetails findByEmail(String email);

}
