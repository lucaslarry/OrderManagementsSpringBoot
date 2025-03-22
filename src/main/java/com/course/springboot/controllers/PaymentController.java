package com.course.springboot.controllers;

import com.course.springboot.dto.payment.PaymentCreateDTO;
import com.course.springboot.dto.payment.PaymentDTO;

import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pay")
@Tag(name = "payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentCreateDTO paymentDTO)
            throws RegraDeNegocioException, BancoDeDadosException {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@RequestParam("id") Long id) throws RegraDeNegocioException {
        PaymentDTO payment = paymentService.findById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }


}