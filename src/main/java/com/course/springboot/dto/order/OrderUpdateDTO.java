package com.course.springboot.dto.order;

import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.OrderItem;
import com.course.springboot.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderUpdateDTO {

    @Schema(description = "List of product IDs in the order", example = "[1, 2, 3]")
    @NotNull(message = "Product IDs are required")
    @Size(min = 1, message = "At least one product is required")
    private List<Long> productIds;
}