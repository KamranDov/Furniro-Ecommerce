package az.crocusoft.ecommerce.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "long_description", length = 2000)
    private String longDescription;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "main_image_id", referencedColumnName = "image_id")
    private Image mainImage;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductVariation> variations;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;


    public Double getProductRating() {
        if (reviews == null || reviews.isEmpty())
            return null;
        return reviews
                .stream()
                .mapToDouble(Review::getRating)
                .sum() / reviews.size();
    }

    public Double getPrice() {
        if (variations == null || variations.isEmpty())
            return null;
        return variations
                .stream()
                .mapToDouble(ProductVariation::getPrice)
                .min()
                .getAsDouble();
    }

    public Double getSpecialPrice() {
        if (variations == null || variations.isEmpty())
            return null;
        return variations
                .stream()
                .mapToDouble(ProductVariation::getSpecialPrice)
                .min()
                .getAsDouble();
    }

    public Double getDiscount() {
        if (variations == null || variations.isEmpty())
            return null;
        return variations
                .stream()
                .mapToDouble(ProductVariation::getDiscount)
                .max()
                .getAsDouble();
    }

}
