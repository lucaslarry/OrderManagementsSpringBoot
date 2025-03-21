package com.course.springboot.services;

import com.course.springboot.dto.user.UserCreateDTO;
import com.course.springboot.dto.user.UserDTO;
import com.course.springboot.entities.Role;
import com.course.springboot.entities.User;
import com.course.springboot.exceptions.BancoDeDadosException;
import com.course.springboot.exceptions.RegraDeNegocioException;
import com.course.springboot.repositories.RoleRepository;
import com.course.springboot.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RoleRepository roleRepository;


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

    public UserDTO insert(UserCreateDTO userCreateDTO) throws Exception {
        User entity = objectMapper.convertValue(userCreateDTO, User.class);

        if (repository.existsByEmail(userCreateDTO.getEmail())) {
            throw new RegraDeNegocioException("Já existe um usuário com este email cadastrado.", HttpStatus.BAD_REQUEST);
        }

        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        entity.setPassword(encoder.encode(userCreateDTO.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role defaultRole = roleRepository.findById(2L)
                .orElseThrow(() -> new Exception("Cargo não encontrado"));
        roles.add(defaultRole);

        entity.setRoles(roles);

        return objectMapper.convertValue(repository.save(entity), UserDTO.class);
    }


    public UserDTO insertAdmin(UserCreateDTO userCreateDTO) throws Exception {
        User entity = objectMapper.convertValue(userCreateDTO, User.class);

        if (repository.existsByEmail(userCreateDTO.getEmail())) {
            throw new RegraDeNegocioException("Já existe um usuário com este email cadastrado.", HttpStatus.BAD_REQUEST);
        }

        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        entity.setPassword(encoder.encode(userCreateDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (userCreateDTO.getRoleIds() != null) {
            roles.addAll(roleRepository.findAllById(userCreateDTO.getRoleIds()));
            if (roles.isEmpty()) {
                throw new RegraDeNegocioException("Cargo não encontrado", HttpStatus.BAD_REQUEST);
            }
        } else {
            Role defaultRole = roleRepository.findById(2L)
                    .orElseThrow(() -> new Exception("Cargo não encontrado"));
            roles.add(defaultRole);
        }

        entity.setRoles(roles);

        return objectMapper.convertValue(repository.save(entity), UserDTO.class);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }
}
