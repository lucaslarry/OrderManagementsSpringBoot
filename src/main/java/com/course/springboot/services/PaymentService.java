package com.course.springboot.services;

import com.course.springboot.dto.order.OrderCreateDTO;
import com.course.springboot.dto.order.OrderDTO;
import com.course.springboot.dto.order.OrderStatusUpdateDTO;
import com.course.springboot.dto.payment.PaymentCreateDTO;
import com.course.springboot.dto.payment.PaymentDTO;
import com.course.springboot.entities.Order;
import com.course.springboot.entities.Payment;
import com.course.springboot.enums.OrderStatus;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.OrderRepository;
import com.course.springboot.repositories.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;


    public PaymentDTO createPayment(PaymentCreateDTO paymentCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Order order = orderService.findEntityById(paymentCreateDTO.getOrderId());

            if(order==null) {
               throw  new RegraDeNegocioException("Pedido não encontrado", HttpStatus.NOT_FOUND);
            }

            if (order.getPayment() != null) {
                throw new RegraDeNegocioException("O pedido já possui um pagamento", HttpStatus.BAD_REQUEST);
            }

            Payment payment = new Payment();
            payment.setMoment(Instant.now());
            payment.setOrder(order);

            payment = paymentRepository.save(payment);
            OrderStatusUpdateDTO orderStatusUpdateDTO = new OrderStatusUpdateDTO();
            orderStatusUpdateDTO.setOrderStatus(OrderStatus.PAID);
            orderService.updateStatus(paymentCreateDTO.getOrderId(), orderStatusUpdateDTO);

            return objectMapper.convertValue(payment, PaymentDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao criar pagamento: " + e.getMessage());
        }
    }

    public PaymentDTO findById(Long id) throws RegraDeNegocioException {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(value -> objectMapper.convertValue(value, PaymentDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("Pagamento não encontrado", HttpStatus.NOT_FOUND));
    }


}