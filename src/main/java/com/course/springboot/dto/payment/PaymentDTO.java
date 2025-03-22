package com.course.springboot.dto.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDTO {

    private Long id;

    @Schema(description = "Order creation date", example = "2023-10-01T12:00:00")
    private Instant moment;


}