package az.crocusoft.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDTO {
    private Long productVariationId;
    private String color;
    private String size;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private Long productId;
}
