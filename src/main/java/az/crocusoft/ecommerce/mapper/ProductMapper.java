package az.crocusoft.ecommerce.mapper;

import az.crocusoft.ecommerce.dto.response.ProductResponse;
import az.crocusoft.ecommerce.dto.ProductVariationDTO;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .title(product.getTitle())
                .isNew(product.isNew())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .discountPrice(product.getSpecialPrice())
                .imageURL(product.getMainImage().getImageUrl())
                .build();
        return productResponse;
    }


    public ProductVariationDTO toProductVariationDTO(ProductVariation variation) {
        ProductVariationDTO productVariationDTO = new ProductVariationDTO();
        productVariationDTO.setId(variation.getId());
        productVariationDTO.setPrice(variation.getPrice());
        productVariationDTO.setDiscount(variation.getDiscount());
        productVariationDTO.setQuantity(variation.getStockQuantity());
        productVariationDTO.setSpecialPrice(variation.getSpecialPrice());
        productVariationDTO.setColor(variation.getColor());
        productVariationDTO.setSize(variation.getSize());
        variation.getImages().forEach(image -> productVariationDTO.getImageUrls().add(image.getImageUrl()));
        return productVariationDTO;
    }
}
