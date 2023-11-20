package az.crocusoft.ecommerce.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "checkout")

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CheckOut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer address_id;
    private String firstName;
    private String lastName;

    private String companyHome;
    private String country;
    private String streetAddress;
    private String city;
    private String province;
    private String zipcode;
    private String phone;
    private String email;
    private  String information;





}
