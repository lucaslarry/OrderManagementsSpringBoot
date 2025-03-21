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
    @Schema(example = "User's email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @NotNull
    private String login;

    @Schema(example = "User's password")
    @NotNull
    @NotBlank(message = "Password is required")
    private String password;
}
