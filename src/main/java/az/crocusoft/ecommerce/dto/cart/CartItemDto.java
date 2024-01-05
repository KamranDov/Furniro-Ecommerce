package az.crocusoft.ecommerce.dto.cart;

import az.crocusoft.ecommerce.model.Cart;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor

public class CartItemDto {

    private Long id;
    private Integer quantity;
    private Long productId;
    private String productName;
    private Long variationId;
    private String sku;
    private Double price;
    private Double discount;
    private String color;
    private String size;
    private Integer stockQuantity;
    private Set<String> imageUrls = new HashSet<>();
    private Double subtotal;  // Yeni eklenen subtotal alanı

    public CartItemDto(Long id, Integer quantity, Long productId, String productName, Long variationId, String sku, Double price, Double discount, String color, String size, Set<String> imageUrls, String productTitle, Double subtotal) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.variationId = variationId;
        this.sku = sku;
        this.price = price;
        this.discount = discount;
        this.color = color;
        this.size = size;
        this.imageUrls = imageUrls;
        this.subtotal = subtotal;
    }

    //    public CartItemDto(Cart cart) {
//        this.id = cart.getId();
//        this.quantity = cart.getQuantity();
//        this.setProductVariation(cart.getProductVariation());
//        this.productTitle = cart.getProductVariation().getProduct().getTitle();
//    }
}
