package com.course.springboot.controllers;

import com.course.springboot.docs.ProductControllerDoc;
import com.course.springboot.dto.product.ProductCreateDTO;
import com.course.springboot.dto.product.ProductDTO;
import com.course.springboot.dto.product.ProductUpdateDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductControllerDoc {

    private final ProductService productService;

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
    public ResponseEntity<ProductDTO> insert(@RequestBody @Valid ProductCreateDTO productCreateDTO) {
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
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody @Valid ProductUpdateDTO productUpdateDTO) {
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
    public ResponseEntity<String> delete(@PathVariable Long id) {
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