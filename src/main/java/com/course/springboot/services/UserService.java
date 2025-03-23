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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;

    public List<UserDTO> findAll() throws BancoDeDadosException {
        return repository.findAll().stream()
                .map(user -> objectMapper.convertValue(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) throws RegraDeNegocioException {
        if (id == null) {
            throw new RegraDeNegocioException("User ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        Optional<User> obj = repository.findById(id);
        return obj.map(user -> objectMapper.convertValue(user, UserDTO.class))
                .orElseThrow(() -> new RegraDeNegocioException("User not found", HttpStatus.NOT_FOUND));
    }

    public UserDTO insert(UserCreateDTO userCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        if (userCreateDTO == null) {
            throw new RegraDeNegocioException("User DTO cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByEmail(userCreateDTO.getEmail())) {
            throw new RegraDeNegocioException("A user with this email already exists", HttpStatus.BAD_REQUEST);
        }

        User entity = objectMapper.convertValue(userCreateDTO, User.class);

        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        entity.setPassword(encoder.encode(userCreateDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RegraDeNegocioException("Role not found", HttpStatus.NOT_FOUND));
        roles.add(defaultRole);

        entity.setRoles(roles);

        return objectMapper.convertValue(repository.save(entity), UserDTO.class);
    }

    public UserDTO insertAdmin(UserCreateDTO userCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        if (userCreateDTO == null) {
            throw new RegraDeNegocioException("User DTO cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByEmail(userCreateDTO.getEmail())) {
            throw new RegraDeNegocioException("A user with this email already exists", HttpStatus.BAD_REQUEST);
        }

        User entity = objectMapper.convertValue(userCreateDTO, User.class);

        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        entity.setPassword(encoder.encode(userCreateDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (userCreateDTO.getRoleIds() != null) {
            roles.addAll(roleRepository.findAllById(userCreateDTO.getRoleIds()));
            if (roles.isEmpty()) {
                throw new RegraDeNegocioException("Role not found", HttpStatus.BAD_REQUEST);
            }
        } else {
            Role defaultRole = roleRepository.findById(2L)
                    .orElseThrow(() -> new RegraDeNegocioException("Role not found", HttpStatus.NOT_FOUND));
            roles.add(defaultRole);
        }

        entity.setRoles(roles);

        return objectMapper.convertValue(repository.save(entity), UserDTO.class);
    }

    public void delete(Long id) throws RegraDeNegocioException, BancoDeDadosException {
        if (id == null) {
            throw new RegraDeNegocioException("User ID cannot be null", HttpStatus.BAD_REQUEST);
        }

        User user = repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("User not found", HttpStatus.NOT_FOUND));
        repository.delete(user);
    }

    public UserDTO update(Long id, UserDTO obj) throws RegraDeNegocioException, BancoDeDadosException {
        if (id == null) {
            throw new RegraDeNegocioException("User ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (obj == null) {
            throw new RegraDeNegocioException("User DTO cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (!id.equals(obj.getId())) {
            throw new RegraDeNegocioException("You can only update your own user", HttpStatus.BAD_REQUEST);
        }

        User user = repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("User not found", HttpStatus.NOT_FOUND));
        updateData(obj, user);
        return objectMapper.convertValue(repository.save(user), UserDTO.class);
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