package az.crocusoft.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderItem {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "orderItem_Id")
     private Long id;
//     @ManyToOne
//     @JoinColumn(name = "product_id")
//     private Product product;
     private  int quantity;

     @ManyToOne
     @JoinColumn(name = "order_id")
     private Order order;

}
