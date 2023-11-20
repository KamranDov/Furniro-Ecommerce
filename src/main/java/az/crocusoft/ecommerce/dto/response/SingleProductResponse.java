package az.crocusoft.ecommerce.dto.response;

import az.crocusoft.ecommerce.dto.ProductVariationDTO;
import az.crocusoft.ecommerce.dto.ReviewDTO;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.model.product.Review;
import az.crocusoft.ecommerce.model.product.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleProductResponse {
    private Long id;
    private String name;
    private String title;
    private String categoryName;
    private List<String> tags;
    private int reviewCount;
    private Double rating;
    private String description;
    private String longDescription;
    private List<ProductVariationDTO> productVariations;
}
