package com.course.springboot.services;

import com.course.springboot.dto.payment.PaymentCreateDTO;
import com.course.springboot.entities.Order;
import com.course.springboot.entities.Payment;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.OrderRepository;
import com.course.springboot.repositories.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public PaymentCreateDTO createPayment(PaymentCreateDTO paymentCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Order order = orderRepository.findById(paymentCreateDTO.getOrderId())
                    .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado", HttpStatus.NOT_FOUND));

            if (order.getPayment() != null) {
                throw new RegraDeNegocioException("O pedido já possui um pagamento", HttpStatus.BAD_REQUEST);
            }

            Payment payment = new Payment();
            payment.setMoment(paymentCreateDTO.getMoment());
            payment.setOrder(order);

            payment = paymentRepository.save(payment);

            order.setPayment(payment);
            orderRepository.save(order);

            return objectMapper.convertValue(payment, PaymentCreateDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao criar pagamento: " + e.getMessage());
        }
    }

    public PaymentCreateDTO findById(Long id) throws RegraDeNegocioException {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(value -> objectMapper.convertValue(value, PaymentCreateDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("Pagamento não encontrado", HttpStatus.NOT_FOUND));
    }

    public PaymentCreateDTO updatePayment(Long id, PaymentCreateDTO paymentCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Payment payment = paymentRepository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Pagamento não encontrado", HttpStatus.NOT_FOUND));

            payment.setMoment(paymentCreateDTO.getMoment());

            payment = paymentRepository.save(payment);

            return objectMapper.convertValue(payment, PaymentCreateDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao atualizar pagamento: " + e.getMessage());
        }
    }

    public void deletePayment(Long id) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Payment payment = paymentRepository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Pagamento não encontrado", HttpStatus.NOT_FOUND));

            Order order = payment.getOrder();
            order.setPayment(null);
            orderRepository.save(order);

            paymentRepository.delete(payment);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao excluir pagamento: " + e.getMessage());
        }
    }
}