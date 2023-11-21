package az.crocusoft.ecommerce.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CheckOut> checkOutList =new ArrayList<>();

}
