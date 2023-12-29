package az.crocusoft.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
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
    private String information;
    @Enumerated(EnumType.STRING)
    private OrderStatusValues orderStatus;
    private LocalDate orderDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE })
    private List<OrderItem> orderItems = new ArrayList<>();



}
