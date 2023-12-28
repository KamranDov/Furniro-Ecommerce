package az.crocusoft.ecommerce.dto;


import az.crocusoft.ecommerce.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private String name;
    private String surname;

    @NotBlank(message = "Username is required")
    @NotEmpty
    @Size(min = 2, message = "user name should have atleast 2 characters.")
    private String username;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"

    )
    private String password;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;








}
