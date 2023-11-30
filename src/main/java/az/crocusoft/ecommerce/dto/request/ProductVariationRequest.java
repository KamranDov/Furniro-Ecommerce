package az.crocusoft.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariationRequest {

    private String sku;
    private String color;
    private String size;
    private Double price;
    private Double discount;
    private Integer stockQuantity;
}
