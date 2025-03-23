package com.course.springboot.controllers;

import com.course.springboot.docs.CategoryControllerDoc;
import com.course.springboot.dto.category.CategoryCreateDTO;
import com.course.springboot.dto.category.CategoryDTO;
import com.course.springboot.entities.Category;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDoc {

    private final CategoryService service;
    
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() throws BancoDeDadosException {
        List<CategoryDTO> list = service.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) throws RegraDeNegocioException {
        CategoryDTO obj = service.findById(id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryCreateDTO categoryCreateDTO) throws BancoDeDadosException {
        CategoryDTO entity = service.insert(categoryCreateDTO);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) throws RegraDeNegocioException, BancoDeDadosException {
        categoryDTO = service.update(id, categoryDTO);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws RegraDeNegocioException, BancoDeDadosException {
        service.delete(id);
        return new ResponseEntity<>("Categoria deletada com sucesso!", HttpStatus.OK);
    }
}