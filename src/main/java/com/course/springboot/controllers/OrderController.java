package com.course.springboot.controllers;

import com.course.springboot.docs.OrderControllerDoc;
import com.course.springboot.dto.order.OrderCreateDTO;
import com.course.springboot.dto.order.OrderDTO;
import com.course.springboot.dto.order.OrderStatusUpdateDTO;
import com.course.springboot.dto.order.OrderUpdateDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController implements OrderControllerDoc {


    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() throws BancoDeDadosException {
        List<OrderDTO> list = orderService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) throws RegraDeNegocioException {
        OrderDTO obj = orderService.findById(id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody @Valid OrderCreateDTO orderCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        OrderDTO entity = orderService.insert(orderCreateDTO);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody @Valid OrderUpdateDTO orderUpdateDTO)
            throws BancoDeDadosException, RegraDeNegocioException {
        OrderDTO orderDTO = orderService.update(id, orderUpdateDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusUpdateDTO orderUpdateDTO)
            throws BancoDeDadosException, RegraDeNegocioException {
        OrderDTO orderDTO = orderService.updateStatus(id, orderUpdateDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }


}