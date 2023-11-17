package az.crocusoft.ecommerce.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "furniture_designations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureDesignation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Furniture designation name must not be blank")
    @Size(min = 4, message = "Furniture designation name must contain at least 4 characters")
    private String name;

    private String description;

    @ManyToMany(mappedBy = "furnitureDesignations", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    @Override
    public int hashCode() {
        return Objects.hash(id, name); // include only essential, non-collection fields
    }
}
