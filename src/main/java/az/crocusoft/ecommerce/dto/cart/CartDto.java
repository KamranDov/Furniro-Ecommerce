package az.crocusoft.ecommerce.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartDto{

    private List<CartItemDto> cartItems;
    private Double totalPrice;
    private Double totalPriceWithoutDiscount;

}
