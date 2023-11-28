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
public class BlogMainDto {
    private Long pid;
    private String title;
    private String content;
    private String imageUrl;
    private Date date;
    private Integer categoryId;
    private Long userId;
    private List<Long> commentsId;
}
