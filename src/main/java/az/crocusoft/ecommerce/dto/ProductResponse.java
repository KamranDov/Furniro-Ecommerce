package az.crocusoft.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String title;
    private boolean isNew;
    private Double price;
    private Double discount;
    private Double discountPrice;
    private String imageURL;

}
