package az.crocusoft.ecommerce.dto.cart;

import az.crocusoft.ecommerce.model.Cart;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItemDto {

    private Long id;
    private Integer quantity;
    private ProductVariation productVariation;

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProductVariation(cart.getProductVariation());
    }
}
