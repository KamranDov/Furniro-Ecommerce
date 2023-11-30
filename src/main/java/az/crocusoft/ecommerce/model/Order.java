package az.crocusoft.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_Id")
    private Long id;
    private Double totalAmount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusValues orderStatus;
    private LocalDate orderDate;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL) //CascadeType.ALL means all operations (persist, remove, merge, refresh) will be cascaded
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;


}
