package az.crocusoft.ecommerce.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String title;

    @Column(length = 255)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "long_description_id", referencedColumnName = "long_description_id")
    private LongDescription longDescription;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "main_image_id")
    private Image mainImage;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductVariation> variations;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "product_designations",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "designation_id")
    )
    private Set<FurnitureDesignation> furnitureDesignations = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getProducts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getProducts().remove(this);
    }

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

    //If any of the variations is discounted, then the product is discounted
    public Boolean isDiscounted() {
        if (variations == null || variations.isEmpty())
            return false;
        return variations
                .stream()
                .anyMatch(ProductVariation::isDiscounted);
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
