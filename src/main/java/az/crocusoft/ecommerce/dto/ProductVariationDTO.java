package az.crocusoft.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDTO {
    private Long id;
    private String color;
    private String size;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private Set<String> imageUrls = new HashSet<>();
}
