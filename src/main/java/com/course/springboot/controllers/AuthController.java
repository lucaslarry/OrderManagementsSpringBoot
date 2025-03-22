package com.course.springboot.controllers;

import com.course.springboot.docs.AuthControllerDoc;
import com.course.springboot.dto.auth.LoginDTO;
import com.course.springboot.dto.auth.TokenDTO;
import com.course.springboot.dto.user.UserCreateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.User;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.security.TokenService;
import com.course.springboot.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "auth")
public class AuthController implements AuthControllerDoc {

    public final AuthenticationManager authenticationManager;

    public final UserService userService;

    public final TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getLogin(),
                            loginDTO.getPassword()
                    );

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            User usuarioValidado = (User) authentication.getPrincipal();

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(tokenService.generateToken(usuarioValidado));

            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            throw new RegraDeNegocioException("E-mail ou senha inválidos. Tente novamente.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCreateDTO obj) throws Exception {
        UserDTO entity = userService.insertAdmin(obj);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }


}

