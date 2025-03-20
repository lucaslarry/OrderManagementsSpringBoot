package com.course.springboot.dto.user;

import com.course.springboot.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Set<Role> roles;


}
