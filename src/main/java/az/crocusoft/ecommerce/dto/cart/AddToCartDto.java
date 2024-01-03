package az.crocusoft.ecommerce.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddToCartDto {

    private @NotNull Long productId;
    private @NotNull Long productVariationId;
    private @NotNull Integer quantity;

}

