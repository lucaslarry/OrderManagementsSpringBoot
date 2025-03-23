package com.course.springboot.services;

import com.course.springboot.dto.product.ProductCreateDTO;
import com.course.springboot.dto.product.ProductDTO;
import com.course.springboot.dto.product.ProductUpdateDTO;
import com.course.springboot.entities.Category;
import com.course.springboot.entities.Product;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository repository;

    private final ObjectMapper objectMapper;

    private final CategoryService categoryService;

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

            Product product = new Product();
            product.setName(productCreateDTO.getName());
            product.setDescription(productCreateDTO.getDescription());
            product.setPrice(productCreateDTO.getPrice().doubleValue());
            product.setImageUrl(productCreateDTO.getImageUrl());

            Set<Category> categories = createCategories(productCreateDTO.getCategoriesID());
            product.setCategories(categories);

            Product savedProduct = repository.save(product);

            return objectMapper.convertValue(savedProduct, ProductDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao inserir produto: " + e.getMessage());
        }
    }

    private Set<Category> createCategories(List<Long> categoryIds) throws RegraDeNegocioException {
        Set<Category> categories = new HashSet<>();

        for (Long categoryId : categoryIds) {
            Category category = objectMapper.convertValue(categoryService.findById(categoryId), Category.class);
            if(category != null) {
                categories.add(category);
            }

        }

        return categories;
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

    private void updateData(ProductUpdateDTO productUpdateDTO, Product product) throws RegraDeNegocioException {
        product.setName(productUpdateDTO.getName());
        product.setDescription(productUpdateDTO.getDescription());
        product.setPrice(productUpdateDTO.getPrice().doubleValue());
        product.setImageUrl(productUpdateDTO.getImageUrl());
        product.setCategories(createCategories(productUpdateDTO.getCategoriesID()));
    }
}