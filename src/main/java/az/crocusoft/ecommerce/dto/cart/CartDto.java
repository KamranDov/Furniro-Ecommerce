package az.crocusoft.ecommerce.dto.cart;

import az.crocusoft.ecommerce.model.product.ProductVariation;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartDto{

    @JsonManagedReference
    private List<CartItemDto> cartItems;
    private Double totalPrice;

}
