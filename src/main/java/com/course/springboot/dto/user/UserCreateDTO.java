package com.course.springboot.dto.user;

import com.course.springboot.entities.Role;
import lombok.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Set<Role> roles;

}
