package com.course.springboot.services;

import com.course.springboot.dto.category.CategoryCreateDTO;
import com.course.springboot.dto.category.CategoryDTO;
import com.course.springboot.entities.Category;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final ObjectMapper objectMapper;

    public List<CategoryDTO> findAll() throws BancoDeDadosException {
        return repository.findAll().stream()
                .map(category -> objectMapper.convertValue(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) throws RegraDeNegocioException {
        if (id == null) {
            throw new RegraDeNegocioException("Category ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> obj = repository.findById(id);
        return obj.map(category -> objectMapper.convertValue(category, CategoryDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("Category not found", HttpStatus.NOT_FOUND));
    }

    public CategoryDTO insert(CategoryCreateDTO categoryCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        if (categoryCreateDTO == null) {
            throw new RegraDeNegocioException("Category DTO cannot be null", HttpStatus.BAD_REQUEST);
        }
        Category entity = objectMapper.convertValue(categoryCreateDTO, Category.class);
        return objectMapper.convertValue(repository.save(entity), CategoryDTO.class);
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) throws RegraDeNegocioException, BancoDeDadosException {
        if (id == null) {
            throw new RegraDeNegocioException("Category ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        Category entity = repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Category not found", HttpStatus.NOT_FOUND));
        updateData(categoryDTO, entity);
        return objectMapper.convertValue(repository.save(entity), CategoryDTO.class);
    }

    public void delete(Long id) throws RegraDeNegocioException {
        if (id == null) {
            throw new RegraDeNegocioException("Category ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        Category entity = repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Category not found", HttpStatus.NOT_FOUND));
        repository.delete(entity);
    }

    private void updateData(CategoryDTO categoryDTO, Category entity) throws RegraDeNegocioException {
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            throw new RegraDeNegocioException("Category name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        entity.setName(categoryDTO.getName());
    }
}