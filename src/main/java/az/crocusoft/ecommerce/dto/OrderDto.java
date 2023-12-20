package az.crocusoft.ecommerce.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @NotBlank(message = "Firstname is required")
    @NotEmpty
    @Column(nullable = false)
    // @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name must not contain numbers or special characters")
    private String firstName;
    @NotBlank(message = "LastName is required")
    @NotEmpty
    @Column(nullable = false)
    // @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name must not contain numbers or special characters")
    private String lastName;

    private String companyHome;
    @NotBlank(message = "Country is required")
    @NotEmpty
    @Column(nullable = false)
    private String country;
    @NotBlank(message = "StreetAddress is required")
    @NotEmpty
    @Column(nullable = false)
    private String streetAddress;
    @Column(nullable = false)
    @NotBlank(message = "City is required")
    @NotEmpty
    private String city;
    @Column(nullable = false)
    private String province;
    @Column(nullable = false)
    private String zipcode;
    @Column(nullable = false)
    @Size(min = 10, max = 20, message = "Mobile Number must be exactly  at leasth 10 digits long")
    @Pattern(regexp = "^\\d{10,20}$", message = "Mobile Number must contain only Numbers")
    private String phone;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    private  String information;

}
