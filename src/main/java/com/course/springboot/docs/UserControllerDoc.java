package com.course.springboot.docs;

import com.course.springboot.dto.user.UserCreateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Endpoints for managing users")
@RequestMapping("/users")
public interface UserControllerDoc {

    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all users available in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping
    ResponseEntity<List<UserDTO>> findAll() throws BancoDeDadosException;

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a specific user by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable Long id) throws RegraDeNegocioException;

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PostMapping
    ResponseEntity<UserDTO> insert(@RequestBody UserCreateDTO userCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;


    @Operation(
            summary = "Update a user",
            description = "Updates an existing user with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @PutMapping("/{id}")
    ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(
            summary = "Delete a user",
            description = "Deletes a user by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) throws RegraDeNegocioException, BancoDeDadosException;
}