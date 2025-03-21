package com.course.springboot.dto.user;

import com.course.springboot.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;

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

    private Set<Role> roles;


}
