package az.crocusoft.ecommerce.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotBlank
    @Size(min = 3, message = "Category name must contain at least 5 characters")
    private String name;

    @OneToMany(mappedBy = "category", cascade =  CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Product> products;

}
