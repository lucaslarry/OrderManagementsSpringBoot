package com.course.springboot.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {

    @NotNull(message = "ID do produto é obrigatório")
    private Long productId;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantity;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double subTotal;
}