package az.crocusoft.ecommerce.model.wishlist;

import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@Table(name = "wish_lists")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    ProductVariation productVariation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


}
