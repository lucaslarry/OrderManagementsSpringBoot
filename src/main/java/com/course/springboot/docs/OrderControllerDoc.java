package com.course.springboot.docs;

import com.course.springboot.dto.order.OrderCreateDTO;
import com.course.springboot.dto.order.OrderDTO;
import com.course.springboot.dto.order.OrderStatusUpdateDTO;
import com.course.springboot.dto.order.OrderUpdateDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")
public interface OrderControllerDoc {

    @Operation(
            summary = "Get all orders",
            description = "Retrieves a list of all orders available in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping
    ResponseEntity<List<OrderDTO>> findAll() throws BancoDeDadosException;

    @Operation(
            summary = "Get order by ID",
            description = "Retrieves a specific order by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> findById(@PathVariable Long id) throws RegraDeNegocioException;

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PostMapping
    ResponseEntity<OrderDTO> insert(@RequestBody OrderCreateDTO orderCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(
            summary = "Update order status",
            description = "Updates the status of an existing order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PutMapping("/{id}/status")
    ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(
            summary = "Update order",
            description = "Updates an existing order with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PutMapping("/{id}")
    ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderUpdateDTO orderUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException;

}