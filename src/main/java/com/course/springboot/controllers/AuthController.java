package com.course.springboot.controllers;

import com.course.springboot.dto.auth.LoginDTO;
import com.course.springboot.dto.auth.TokenDTO;
import com.course.springboot.entities.User;
import com.course.springboot.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getLogin(),
                            loginDTO.getPassword()
                    );


            TokenDTO tokenDTO = new TokenDTO();

            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            throw new RegraDeNegocioException("E-mail ou senha inválidos. Tente novamente.", HttpStatus.NOT_FOUND);
        }
    }


}

