package az.crocusoft.ecommerce.model;

import az.crocusoft.ecommerce.model.product.ProductVariation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer quantity;
    private Double totalPrice;

    @Column(name = "discounted_price")
    private Double discountedPrice;
}
