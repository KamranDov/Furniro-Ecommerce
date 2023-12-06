package az.crocusoft.ecommerce.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @Column(nullable = false)
    // @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name must not contain numbers or special characters")
    private String firstName;
    @Column(nullable = false)
    // @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name must not contain numbers or special characters")
    private String lastName;
    private String companyHome;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String streetAddress;
    @Column(nullable = false)
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
