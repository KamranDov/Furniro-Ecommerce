package az.crocusoft.ecommerce.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_variations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_variation_id")
    private Long id;
    private String SKU;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String color;
    private String size;

    @Column(nullable = false)
    private Double price;
    private Double discount;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_variation_images",
            joinColumns = @JoinColumn(name = "variation_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> images = new HashSet<>();


    public Double getSpecialPrice() {
        if (discount == null || discount == 0)
            return price;
        return price - (price * discount / 100);
    }

    public boolean isDiscounted() {
        return discount != null && discount > 0;
    }
}
