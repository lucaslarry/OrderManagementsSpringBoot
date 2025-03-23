package com.course.springboot.services;

import com.course.springboot.dto.order.OrderCreateDTO;
import com.course.springboot.dto.order.OrderDTO;
import com.course.springboot.dto.order.OrderStatusUpdateDTO;
import com.course.springboot.dto.order.OrderUpdateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.*;
import com.course.springboot.enums.OrderStatus;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.OrderRepository;
import com.course.springboot.repositories.OrderItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    public List<OrderDTO> findAll() throws BancoDeDadosException {
        return orderRepository.findAll().stream()
                .map(order -> objectMapper.convertValue(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) throws RegraDeNegocioException {
        if (id == null) {
            throw new RegraDeNegocioException("Order ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Order not found", HttpStatus.NOT_FOUND));
        return objectMapper.convertValue(order, OrderDTO.class);
    }

    public OrderDTO insert(OrderCreateDTO orderCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        if (orderCreateDTO == null) {
            throw new RegraDeNegocioException("Order DTO cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (orderCreateDTO.getClientId() == null) {
            throw new RegraDeNegocioException("Client ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (orderCreateDTO.getProductIds() == null || orderCreateDTO.getProductIds().isEmpty()) {
            throw new RegraDeNegocioException("Product list cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Order order = new Order();
        order.setMoment(Instant.now());
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        UserDTO clientDTO = userService.findById(orderCreateDTO.getClientId());
        User client = objectMapper.convertValue(clientDTO, User.class);
        order.setClient(client);

        orderRepository.save(order);

        Set<OrderItem> orderItems = createOrderItems(order, orderCreateDTO);
        orderItemRepository.saveAll(orderItems);
        order.setItems(orderItems);
        orderRepository.save(order);

        return objectMapper.convertValue(order, OrderDTO.class);
    }

    private Set<OrderItem> createOrderItems(Order order, OrderCreateDTO orderCreateDTO) throws RegraDeNegocioException {
        Set<OrderItem> orderItems = new HashSet<>();

        for (Long productId : orderCreateDTO.getProductIds()) {
            Product product = objectMapper.convertValue(productService.findById(productId), Product.class);

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
        return orderItems;
    }

    public OrderDTO updateStatus(Long id, OrderStatusUpdateDTO orderDTO) throws RegraDeNegocioException, BancoDeDadosException {
        if (id == null) {
            throw new RegraDeNegocioException("Order ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (orderDTO == null) {
            throw new RegraDeNegocioException("Status update DTO cannot be null", HttpStatus.BAD_REQUEST);
        }

        Order entity = orderRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Order not found", HttpStatus.NOT_FOUND));
        updateStatusData(orderDTO, entity);
        return objectMapper.convertValue(orderRepository.save(entity), OrderDTO.class);
    }

    private void updateStatusData(OrderStatusUpdateDTO orderDTO, Order entity) {
        entity.setOrderStatus(orderDTO.getOrderStatus());
    }

    public OrderDTO update(Long id, OrderUpdateDTO orderDTO) throws RegraDeNegocioException, BancoDeDadosException {
        if (id == null) {
            throw new RegraDeNegocioException("Order ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (orderDTO == null) {
            throw new RegraDeNegocioException("Order update DTO cannot be null", HttpStatus.BAD_REQUEST);
        }

        Order entity = orderRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Order not found", HttpStatus.NOT_FOUND));
        updateData(orderDTO, entity);
        return objectMapper.convertValue(orderRepository.save(entity), OrderDTO.class);
    }

    private void updateData(OrderUpdateDTO orderDTO, Order entity) throws RegraDeNegocioException {
        Set<OrderItem> orderItems = entity.getItems();
        orderItemRepository.deleteAll(orderItems);
        OrderCreateDTO orderCreateDTO = objectMapper.convertValue(orderDTO, OrderCreateDTO.class);
        orderItems = createOrderItems(entity, orderCreateDTO);

        orderItemRepository.saveAll(orderItems);
        entity.setItems(orderItems);
    }

    public Order findEntityById(Long id) throws RegraDeNegocioException {
        if (id == null) {
            throw new RegraDeNegocioException("Order ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        return orderRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Order not found", HttpStatus.NOT_FOUND));
    }
}