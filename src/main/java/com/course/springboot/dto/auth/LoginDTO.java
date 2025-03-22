package com.course.springboot.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {
    @Schema(description = "User's email", example = "john.doe@example.com", format = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @NotNull
    private String login;

    @Schema(description = "User's password", example = "Password@123", format = "password", minLength = 8, maxLength = 20)
    @NotNull
    @NotBlank(message = "Password is required")
    private String password;
}
