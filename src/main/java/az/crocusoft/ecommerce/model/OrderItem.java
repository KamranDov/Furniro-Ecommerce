package az.crocusoft.ecommerce.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
     private  int quantity;

     @ManyToOne
     @JoinColumn(name = "order_id")
     private Order order;

}
