package com.course.springboot.services;

import com.course.springboot.dto.order.OrderStatusUpdateDTO;
import com.course.springboot.dto.payment.PaymentCreateDTO;
import com.course.springboot.dto.payment.PaymentDTO;
import com.course.springboot.entities.Order;
import com.course.springboot.entities.Payment;
import com.course.springboot.enums.OrderStatus;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    public PaymentDTO createPayment(PaymentCreateDTO paymentCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        if (paymentCreateDTO == null) {
            throw new RegraDeNegocioException("Payment DTO cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (paymentCreateDTO.getOrderId() == null) {
            throw new RegraDeNegocioException("Order ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        Order order = orderService.findEntityById(paymentCreateDTO.getOrderId());

        if (order.getPayment() != null) {
            throw new RegraDeNegocioException("The order already has a payment", HttpStatus.BAD_REQUEST);
        }

        Payment payment = new Payment();
        payment.setMoment(Instant.now());
        payment.setOrder(order);

        payment = paymentRepository.save(payment);

        OrderStatusUpdateDTO orderStatusUpdateDTO = new OrderStatusUpdateDTO();
        orderStatusUpdateDTO.setOrderStatus(OrderStatus.PAID);
        orderService.updateStatus(paymentCreateDTO.getOrderId(), orderStatusUpdateDTO);

        return objectMapper.convertValue(payment, PaymentDTO.class);
    }

    public PaymentDTO findById(Long id) throws RegraDeNegocioException {
        if (id == null) {
            throw new RegraDeNegocioException("Payment ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(value -> objectMapper.convertValue(value, PaymentDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("Payment not found", HttpStatus.NOT_FOUND));
    }
}