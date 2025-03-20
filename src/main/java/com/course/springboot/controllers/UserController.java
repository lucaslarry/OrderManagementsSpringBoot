package com.course.springboot.controllers;

import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() throws BancoDeDadosException {
        List<UserDTO> list = service.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) throws RegraDeNegocioException {
        UserDTO obj = service.findById(id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO obj) throws BancoDeDadosException {
        obj = service.insert(obj);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws BancoDeDadosException, RegraDeNegocioException {
        service.delete(id);
        return new ResponseEntity<>("Usuario deletado com sucesso!", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO obj) throws BancoDeDadosException, RegraDeNegocioException {
        obj = service.update(id, obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

}
