package az.crocusoft.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReviewRequest (
        @NotBlank(message = "Product should be specified")
        Long productId,
        String comment,
        @NotBlank(message = "Rating should be specified")
        int rating
) {

}
