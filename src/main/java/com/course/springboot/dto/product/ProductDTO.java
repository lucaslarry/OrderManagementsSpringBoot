package com.course.springboot.dto.product;

import com.course.springboot.dto.category.CategoryDTO;
import com.course.springboot.entities.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Product name", example = "Smartphone")
    private String name;

    @Schema(description = "Product description", example = "A high-end smartphone with advanced features")
    private String description;

    @Schema(description = "Product price", example = "999.99")
    private BigDecimal price;

    @Schema(description = "Product image URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "Product category")
    private Set<Category> categories;
}