package com.course.springboot.dto.order;

import com.course.springboot.dto.product.ProductDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.OrderItem;
import com.course.springboot.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    @Schema(description = "Order ID", example = "1")
    private Long id;

    @Schema(description = "Order client")
    private UserDTO client;

    @Schema(description = "List of products in the order")
    private Set<OrderItem> items;

    @Schema(description = "Order creation date", example = "2023-10-01T12:00:00")
    private LocalDateTime moment;

    @Schema(description = "Order Status", example = "PAID")
    private OrderStatus orderStatus;
}