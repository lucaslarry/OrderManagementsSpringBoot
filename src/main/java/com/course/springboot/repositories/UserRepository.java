package com.course.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.springboot.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

}
