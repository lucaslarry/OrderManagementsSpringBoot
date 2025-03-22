package com.course.springboot.dto.user;

import com.course.springboot.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    @Schema(description = "User's full name", example = "Lucas Larry")
    @Size(max = 255, message = "Name must be at most 255 characters long")
    @NotNull
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[^0-9]*$", message = "Name cannot contain numbers")
    private String name;

    @Schema(description = "User's email", example = "lucas.larry@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @NotNull
    private String email;

    @Schema(description = "User's phone number", example = "+5511999999999")
    @NotBlank(message = "Phone number is required")
    @NotNull
    private String phone;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @Schema(description = "User's password", example = "Password@123")
    @NotNull(message = "Password cannot be blank")
    @NotBlank(message = "Password is required")
    private String password;

    @Schema(description = "User's role IDs", example = "[1, 2]")
    private Set<Long> roleIds;

}
