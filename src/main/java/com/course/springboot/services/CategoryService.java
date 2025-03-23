package com.course.springboot.services;

import com.course.springboot.dto.category.CategoryCreateDTO;
import com.course.springboot.dto.category.CategoryDTO;
import com.course.springboot.entities.Category;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            return repository.findAll().stream()
                    .map(category -> objectMapper.convertValue(category, CategoryDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao buscar todas as categorias: " + e.getMessage());
        }
    }


    public CategoryDTO findById(Long id) throws RegraDeNegocioException {
        Optional<Category> obj = repository.findById(id);
        return obj.map(category -> objectMapper.convertValue(category, CategoryDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("Categoria não encontrada", HttpStatus.NOT_FOUND));
    }

    public CategoryDTO insert(CategoryCreateDTO categoryCreateDTO) throws BancoDeDadosException {
        try {
            Category entity = objectMapper.convertValue(categoryCreateDTO, Category.class);
            return objectMapper.convertValue(repository.save(entity), CategoryDTO.class);
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao inserir categoria: " + e.getMessage());
        }
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Category entity = repository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Categoria não encontrada", HttpStatus.NOT_FOUND));
            updateData(categoryDTO, entity);
            return objectMapper.convertValue(repository.save(entity), CategoryDTO.class);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    public void delete(Long id) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Category entity = repository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Categoria não encontrada", HttpStatus.NOT_FOUND));
            repository.delete(entity);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao excluir categoria: " + e.getMessage());
        }
    }

    private void updateData(CategoryDTO categoryDTO, Category entity) {
        entity.setName(categoryDTO.getName());
    }
}