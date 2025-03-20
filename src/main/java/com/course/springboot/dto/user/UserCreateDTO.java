package com.course.springboot.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    private String name;
    private String email;
    private String phone;
    private String password;


}
