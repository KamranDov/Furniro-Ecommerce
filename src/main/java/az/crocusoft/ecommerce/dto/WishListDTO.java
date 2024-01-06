package az.crocusoft.ecommerce.dto;

import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder

public class WishListDTO {

    private Long productId;
    private String productName;
    private Long variationId;
    private String sku;
    private Double price;
    private Double discount;
    private String color;
    private String size;
    private Set<String> imageUrls = new HashSet<>();

    @Builder
    public WishListDTO(Long productId, String productName, Long variationId, String sku, Double price, Double discount, String color, String size, Set<String> imageUrls) {
       this.productId=productId;
        this.productName = productName;
        this.variationId = variationId;
        this.sku = sku;
        this.price = price;
        this.discount = discount;
        this.color = color;
        this.size = size;
        this.imageUrls = imageUrls;
    }


    public WishListDTO() {

    }
}
