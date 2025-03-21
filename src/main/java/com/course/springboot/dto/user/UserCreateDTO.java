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
    @Schema(example = "User's full name")
    @Size(max = 255, message = "Name must be at most 255 characters long")
    @NotNull
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[^0-9]*$", message = "Name cannot contain numbers")
    private String name;

    @Schema(example = "User's email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @NotNull
    private String email;

    @Schema(example = "User's phone number")
    @NotBlank(message = "Phone number is required")
    @NotNull
    private String phone;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @Schema(example = "User's password")
    @NotNull(message = "Password cannot be blank")
    @NotBlank(message = "Password is required")
    private String password;

    private Set<Long> roleIds;

}
