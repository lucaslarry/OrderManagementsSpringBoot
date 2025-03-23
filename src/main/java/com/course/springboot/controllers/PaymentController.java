package com.course.springboot.controllers;

import com.course.springboot.docs.PaymentControllerDoc;
import com.course.springboot.dto.payment.PaymentCreateDTO;
import com.course.springboot.dto.payment.PaymentDTO;

import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/pay")
@RequiredArgsConstructor
public class PaymentController implements PaymentControllerDoc {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid PaymentCreateDTO paymentDTO)
            throws RegraDeNegocioException, BancoDeDadosException {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable Long id ) throws RegraDeNegocioException {
        PaymentDTO payment = paymentService.findById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }


}