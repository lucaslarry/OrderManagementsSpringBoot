package com.course.springboot.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryCreateDTO {

    @Schema(description = "Category name", example = "Electronics")
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be at most 255 characters long")
    @NotNull
    private String name;
}