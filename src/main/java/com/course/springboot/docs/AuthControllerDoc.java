package com.course.springboot.docs;

import com.course.springboot.dto.auth.LoginDTO;
import com.course.springboot.dto.auth.TokenDTO;
import com.course.springboot.dto.user.UserCreateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/auth")
public interface AuthControllerDoc {

    @Operation(summary = "Perform login", description = "Authenticates the user and returns a JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid login data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @PostMapping()
    ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(summary = "Register a new Admin", description = "Creates a new user with admin permissions")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to access this resource"),
            @ApiResponse(responseCode = "500", description = "An exception was thrown")
    })
    @PostMapping("/register")
    ResponseEntity<UserDTO> register(@RequestBody @Valid UserCreateDTO userCreateDTO) throws Exception;
}