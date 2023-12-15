package az.crocusoft.ecommerce.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDto {

    private String title;
    private String content;
    private MultipartFile image;
    private Integer categoryId;


}
