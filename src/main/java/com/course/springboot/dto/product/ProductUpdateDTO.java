package com.course.springboot.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateDTO {

    @Schema(description = "Product name", example = "Smartphone")
    @Size(max = 255, message = "Name must be at most 255 characters long")
    private String name;

    @Schema(description = "Product description", example = "A high-end smartphone with advanced features")
    private String description;

    @Schema(description = "Product price", example = "999.99")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Product image URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "Category ID", example = "1")
    private Long categoryId;
}