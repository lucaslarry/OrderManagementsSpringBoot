package com.course.springboot.controllers;

import com.course.springboot.dto.product.ProductCreateDTO;
import com.course.springboot.dto.product.ProductDTO;
import com.course.springboot.dto.product.ProductUpdateDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Produtos", description = "Operações relacionadas a produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        try {
            List<ProductDTO> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (BancoDeDadosException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductCreateDTO productCreateDTO) {
        try {
            ProductDTO product = productService.insert(productCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        } catch (BancoDeDadosException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductUpdateDTO productUpdateDTO) {
        try {
            ProductDTO product = productService.update(id, productUpdateDTO);
            return ResponseEntity.ok(product);
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        } catch (BancoDeDadosException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(e.getStatus()).build();
        } catch (BancoDeDadosException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}