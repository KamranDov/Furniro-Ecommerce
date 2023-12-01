package az.crocusoft.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogCategoryDto {
    private Integer cid;
    private String name;
    private String description;
}
