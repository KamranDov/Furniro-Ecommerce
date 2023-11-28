package az.crocusoft.ecommerce.dto;

import az.crocusoft.ecommerce.model.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long uid;
    private String name;
    private String username;
    private String password;
    private String about;
    private List<Role> roles;
}
