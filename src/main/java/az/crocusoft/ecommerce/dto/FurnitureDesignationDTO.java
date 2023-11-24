package az.crocusoft.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FurnitureDesignationDTO {
    private Long furnitureDesignationId;
    private String furnitureDesignationName;
    private String imageUrl;
}
