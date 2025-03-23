package com.course.springboot.docs;

import com.course.springboot.dto.payment.PaymentCreateDTO;
import com.course.springboot.dto.payment.PaymentDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payments", description = "Endpoints for managing payments")
@RequestMapping("/payments")
public interface PaymentControllerDoc {

    @Operation(
            summary = "Create a new payment",
            description = "Creates a new payment for an existing order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PostMapping
    ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(
            summary = "Get payment by ID",
            description = "Retrieves a specific payment by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping("/{id}")
    ResponseEntity<PaymentDTO> findById(@PathVariable Long id) throws RegraDeNegocioException;
}