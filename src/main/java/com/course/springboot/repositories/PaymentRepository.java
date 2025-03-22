package com.course.springboot.repositories;

import com.course.springboot.entities.Category;
import com.course.springboot.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{

}
