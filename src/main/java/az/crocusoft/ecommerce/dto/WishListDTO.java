package az.crocusoft.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishListDTO {
    private Long productId;
    private Long userId;
}
