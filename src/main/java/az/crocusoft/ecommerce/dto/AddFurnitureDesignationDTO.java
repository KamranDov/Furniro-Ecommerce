package az.crocusoft.ecommerce.dto;

import az.crocusoft.ecommerce.model.product.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFurnitureDesignationDTO {
    private String furnitureDesignationName;
    private String description;
}
