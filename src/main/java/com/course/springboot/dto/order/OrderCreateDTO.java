package com.course.springboot.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreateDTO {

    @Schema(description = "Client ID", example = "1")
    @NotNull(message = "Client ID is required")
    private Long clientId;

    @Schema(description = "List of product IDs in the order", example = "[1, 2, 3]")
    @NotNull(message = "Product IDs are required")
    @Size(min = 1, message = "At least one product is required")
    private Set<Long> productIds;
}