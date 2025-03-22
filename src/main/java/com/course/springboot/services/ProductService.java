package com.course.springboot.services;

import com.course.springboot.dto.product.ProductCreateDTO;
import com.course.springboot.dto.product.ProductDTO;
import com.course.springboot.dto.product.ProductUpdateDTO;
import com.course.springboot.entities.Product;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<ProductDTO> findAll() throws BancoDeDadosException {
        try {
            return repository.findAll().stream()
                    .map(product -> objectMapper.convertValue(product, ProductDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao buscar todos os produtos: " + e.getMessage());
        }
    }

    public ProductDTO findById(Long id) throws RegraDeNegocioException {
        Optional<Product> obj = repository.findById(id);
        return obj.map(product -> objectMapper.convertValue(product, ProductDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado", HttpStatus.NOT_FOUND));
    }

    public ProductDTO insert(ProductCreateDTO productCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            if (repository.existsByName(productCreateDTO.getName())) {
                throw new RegraDeNegocioException("Já existe um produto com este nome cadastrado.", HttpStatus.BAD_REQUEST);
            }

            Product product = objectMapper.convertValue(productCreateDTO, Product.class);

            Product savedProduct = repository.save(product);

            return objectMapper.convertValue(savedProduct, ProductDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public ProductDTO update(Long id, ProductUpdateDTO productUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Product product = repository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado", HttpStatus.NOT_FOUND));

            updateData(productUpdateDTO, product);

            Product updatedProduct = repository.save(product);

            return objectMapper.convertValue(updatedProduct, ProductDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void delete(Long id) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Product product = repository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado", HttpStatus.NOT_FOUND));
            repository.delete(product);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao excluir produto: " + e.getMessage());
        }
    }

    private void updateData(ProductUpdateDTO productUpdateDTO, Product product) {
        product.setName(productUpdateDTO.getName());
        product.setDescription(productUpdateDTO.getDescription());
        product.setPrice(productUpdateDTO.getPrice().doubleValue());
        product.setImageUrl(productUpdateDTO.getImageUrl());
    }
}