package com.course.springboot.dto.order;

import com.course.springboot.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusUpdateDTO {

    @Schema(description = "Order Status", example = "PAID , WAITING_PAYMENT , SHIPPED , DELIVERED , CANCELED")
    private OrderStatus orderStatus;
}