package com.course.springboot.services;

import com.course.springboot.dto.order.OrderCreateDTO;
import com.course.springboot.dto.order.OrderDTO;
import com.course.springboot.dto.order.OrderUpdateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.*;
import com.course.springboot.enums.OrderStatus;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.OrderRepository;
import com.course.springboot.repositories.OrderItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    public List<OrderDTO> findAll() throws BancoDeDadosException {
        try {
            return orderRepository.findAll().stream()
                    .map(order -> objectMapper.convertValue(order, OrderDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao buscar todos os pedidos: " + e.getMessage());
        }
    }

    public OrderDTO findById(Long id) throws RegraDeNegocioException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado", HttpStatus.NOT_FOUND));
        return objectMapper.convertValue(order, OrderDTO.class);
    }

    public OrderDTO insert(OrderCreateDTO orderCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Order order = new Order();
            order.setMoment(Instant.now());
            order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

            UserDTO clientDTO = userService.findById(orderCreateDTO.getClientId());
            User client = objectMapper.convertValue(clientDTO, User.class);
            order.setClient(client);

            orderRepository.save(order);

            Set<OrderItem> orderItems = new HashSet<>();

            for (Long productId : orderCreateDTO.getProductIds()) {
                Product product = productService.findById(productId);

                OrderItem existingOrderItem = orderItems.stream()
                        .filter(item -> item.getProduct().equals(product))
                        .findFirst()
                        .orElse(null);

                if (existingOrderItem != null) {
                    existingOrderItem.setQuantity(existingOrderItem.getQuantity() + 1);
                } else {
                    orderItems.add(new OrderItem(order, product, 1, product.getPrice()));
                }
            }

            orderItemRepository.saveAll(orderItems);
            order.setItems(orderItems);
            orderRepository.save(order);

            return objectMapper.convertValue(order, OrderDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao inserir pedido: " + e.getMessage());
        }
    }



    public OrderDTO update(Long id, OrderUpdateDTO orderDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Order entity = orderRepository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado", HttpStatus.NOT_FOUND));
            updateData(orderDTO, entity);
            return objectMapper.convertValue(orderRepository.save(entity), OrderDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao atualizar pedido: " + e.getMessage());
        }
    }



    private void updateData(OrderUpdateDTO orderDTO, Order entity) {
        entity.setOrderStatus(orderDTO.getOrderStatus());
    }
}