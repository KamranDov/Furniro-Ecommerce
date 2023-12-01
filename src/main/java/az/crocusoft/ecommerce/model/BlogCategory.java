package az.crocusoft.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;
    @NotEmpty(message = "Category name must not be empty or null")
    private String name;
    @NotEmpty(message = "Category description must not be empty or null")
    private String description;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    List<Blog> posts=new ArrayList<>();
}
