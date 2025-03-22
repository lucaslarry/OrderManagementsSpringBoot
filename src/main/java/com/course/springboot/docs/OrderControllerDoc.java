package com.course.springboot.docs;

import com.course.springboot.entities.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/orders")
public interface OrderControllerDoc {

    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @GetMapping
    ResponseEntity<List<Order>> findAll();

    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @GetMapping("/{id}")
    ResponseEntity<Order> findById(@PathVariable Long id);
}