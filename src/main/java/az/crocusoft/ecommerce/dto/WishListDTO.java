package az.crocusoft.ecommerce.dto;

import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishListDTO {

    private ProductVariation productVariation;
    private String productTitle;



}
