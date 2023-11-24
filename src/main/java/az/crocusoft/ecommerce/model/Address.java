package az.crocusoft.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "checkout")

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_Id")
    private Integer id;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "CheckOut{" +
                "Id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }





}
