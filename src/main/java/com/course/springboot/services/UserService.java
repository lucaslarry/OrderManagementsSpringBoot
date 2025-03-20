package com.course.springboot.services;

import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.User;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    public List<UserDTO> findAll() throws BancoDeDadosException {
        try {
            return repository.findAll().stream()
                    .map(value -> objectMapper.convertValue(value, UserDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao buscar todos os usuários: " + e.getMessage());
        }
    }

    public UserDTO findById(Long id) throws RegraDeNegocioException {
        Optional<User> obj = repository.findById(id);
        return obj.map(value -> objectMapper.convertValue(value, UserDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado", HttpStatus.NOT_FOUND));
    }

    public UserDTO insert(UserDTO obj) throws BancoDeDadosException {
        try {
            User user = objectMapper.convertValue(obj, User.class);
            return objectMapper.convertValue(repository.save(user), UserDTO.class);
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public void delete(Long id) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            User user = repository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado", HttpStatus.NOT_FOUND));
            repository.delete(user);
        } catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    public UserDTO update(Long id, UserDTO obj) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            User user = repository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado", HttpStatus.NOT_FOUND));
            updateData(obj, user);
            return objectMapper.convertValue(repository.save(user), UserDTO.class);
        }
        catch (RegraDeNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    private void updateData(UserDTO obj, User user) {
        user.setName(obj.getName());
        user.setPhone(obj.getPhone());
        user.setEmail(obj.getEmail());
    }
}
