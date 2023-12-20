package az.crocusoft.ecommerce.model;

import az.crocusoft.ecommerce.model.product.ProductVariation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer quantity;

    @Column(name = "discounted_price")
    Double discountedPrice;

    @Column(name = "created_date")
    LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    ProductVariation productVariation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
