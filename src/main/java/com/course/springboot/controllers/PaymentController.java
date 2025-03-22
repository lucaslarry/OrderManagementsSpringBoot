package com.course.springboot.controllers;

import com.course.springboot.dto.payment.PaymentCreateDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payments")
@Tag(name = "payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentCreateDTO> createPayment(@RequestBody PaymentCreateDTO paymentDTO)
            throws RegraDeNegocioException, BancoDeDadosException {
        PaymentCreateDTO createdPayment = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentCreateDTO> findById(@PathVariable Long id) throws RegraDeNegocioException {
        PaymentCreateDTO paymentDTO = paymentService.findById(id);
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PaymentCreateDTO> updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentCreateDTO paymentDTO
    ) throws RegraDeNegocioException, BancoDeDadosException {
        PaymentCreateDTO updatedPayment = paymentService.updatePayment(id, paymentDTO);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id)
            throws RegraDeNegocioException, BancoDeDadosException {
        paymentService.deletePayment(id);
        return new ResponseEntity<>("Pagamento excluído com sucesso!", HttpStatus.OK);
    }
}