package az.crocusoft.ecommerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private String title;
    private boolean isNew;
    private boolean isPublished;
    private String description;
    private String longDescription;
    private List<Long> furnitureDesignationIds;
    private Long categoryId;
    private String tagNames;
}
