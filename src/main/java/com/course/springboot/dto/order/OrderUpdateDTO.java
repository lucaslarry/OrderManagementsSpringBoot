package com.course.springboot.dto.order;

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
public class OrderUpdateDTO {
    @Schema(description = "Order Status", example = "PAID , WAITING_PAYMENT , SHIPPED , DELIVERED , CANCELED")
    private OrderStatus orderStatus;
}