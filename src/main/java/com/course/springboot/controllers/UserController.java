package com.course.springboot.controllers;

import com.course.springboot.docs.UserControllerDoc;
import com.course.springboot.dto.user.UserCreateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDoc {

    private final UserService service;

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

    @PostMapping("/register")
    public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserCreateDTO obj) throws BancoDeDadosException, RegraDeNegocioException {
        UserDTO entity = service.insert(obj);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws BancoDeDadosException, RegraDeNegocioException {
        service.delete(id);
        return new ResponseEntity<>("Usuario deletado com sucesso!", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody @Valid UserDTO obj) throws BancoDeDadosException, RegraDeNegocioException {
        obj = service.update(id, obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

}
