package com.course.springboot.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDTO {

    @Schema(description = "Product name", example = "Smartphone")
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be at most 255 characters long")
    private String name;

    @Schema(description = "Product description", example = "A high-end smartphone with advanced features")
    @NotBlank(message = "Description is required")
    private String description;

    @Schema(description = "Product price", example = "999.99")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Product image URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "Category ID", example = "[1]")
    @NotNull(message = "Category ID is required")
    private List<Long> categoriesID;
}