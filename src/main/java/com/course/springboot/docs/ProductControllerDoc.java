package com.course.springboot.docs;

import com.course.springboot.dto.product.ProductCreateDTO;
import com.course.springboot.dto.product.ProductDTO;
import com.course.springboot.dto.product.ProductUpdateDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Endpoints for managing products")
@RequestMapping("/products")
public interface ProductControllerDoc {

    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all products available in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping
    ResponseEntity<List<ProductDTO>> findAll() throws BancoDeDadosException;

    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a specific product by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> findById(@PathVariable Long id) throws RegraDeNegocioException;

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PostMapping
    ResponseEntity<ProductDTO> insert(@RequestBody ProductCreateDTO productCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PutMapping("/{id}")
    ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductUpdateDTO productUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) throws RegraDeNegocioException, BancoDeDadosException;
}