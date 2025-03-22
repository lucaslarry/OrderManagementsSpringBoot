package com.course.springboot.docs;

import com.course.springboot.dto.category.CategoryDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories")
public interface CategoryControllerDoc {

    @Operation(
            summary = "Get all categories",
            description = "Retrieves a list of all categories available in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping
    ResponseEntity<List<CategoryDTO>> findAll() throws BancoDeDadosException;

    @Operation(
            summary = "Get category by ID",
            description = "Retrieves a specific category by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> findById(@PathVariable Long id) throws RegraDeNegocioException;
}