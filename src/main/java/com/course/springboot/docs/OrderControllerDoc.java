package com.course.springboot.docs;

import com.course.springboot.dto.order.OrderCreateDTO;
import com.course.springboot.dto.order.OrderDTO;
import com.course.springboot.dto.order.OrderUpdateDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@Tag(name = "orders", description = "Endpoints para gerenciamento de pedidos")
public interface OrderControllerDoc {

    @Operation(summary = "Retorna todos os pedidos", description = "Retorna uma lista de todos os pedidos cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    ResponseEntity<List<OrderDTO>> findAll() throws BancoDeDadosException;

    @Operation(summary = "Retorna um pedido por ID", description = "Retorna os detalhes de um pedido específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> findById(@PathVariable Long id) throws RegraDeNegocioException;

    @Operation(summary = "Cria um novo pedido", description = "Cria um novo pedido com base nos dados fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<OrderDTO> insert(@RequestBody OrderCreateDTO orderCreateDTO)
            throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Atualiza um pedido existente", description = "Atualiza os dados de um pedido existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderUpdateDTO OrderUpdateDTO)
            throws BancoDeDadosException, RegraDeNegocioException;

}