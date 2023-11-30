package az.crocusoft.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    @NotBlank(message = "Username is required")
    private String username;


    @NotBlank(message = "Password is required")
    private String password;

}
