package az.crocusoft.ecommerce.dto;

import az.crocusoft.ecommerce.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String name;
    private String surName;
    private String username;
    private String password;
    private String email;
    private Role role;

}
