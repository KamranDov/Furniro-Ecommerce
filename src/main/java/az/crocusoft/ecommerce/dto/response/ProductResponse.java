package az.crocusoft.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
