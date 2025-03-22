package com.course.springboot.docs;

import com.course.springboot.dto.user.UserCreateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
public interface UserControllerDoc {

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @GetMapping
    ResponseEntity<List<UserDTO>> findAll() throws BancoDeDadosException;

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable Long id) throws RegraDeNegocioException;

    @Operation(summary = "Register a new user", description = "Creates a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @PostMapping("/register")
    ResponseEntity<UserDTO> insert(@RequestBody UserCreateDTO userCreateDTO) throws Exception;

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(summary = "Update a user", description = "Updates an existing user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @PutMapping("/{id}")
    ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) throws BancoDeDadosException, RegraDeNegocioException;
}