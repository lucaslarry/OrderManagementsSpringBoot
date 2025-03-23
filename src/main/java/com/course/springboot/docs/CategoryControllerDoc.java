package com.course.springboot.docs;

import com.course.springboot.dto.category.CategoryCreateDTO;
import com.course.springboot.dto.category.CategoryDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "Create a new category",
            description = "Creates a new category with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PostMapping
    ResponseEntity<CategoryDTO> insert(@RequestBody CategoryCreateDTO categoryCreateDTO) throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(
            summary = "Update a category",
            description = "Updates an existing category with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PutMapping("/{id}")
    ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(
            summary = "Delete a category",
            description = "Deletes a category by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) throws RegraDeNegocioException, BancoDeDadosException;
}