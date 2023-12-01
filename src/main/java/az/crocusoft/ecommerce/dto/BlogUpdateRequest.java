package az.crocusoft.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogUpdateRequest {
    private Long pid;
    private String title;
    private String content;
    private String imageName;
    private Date date;
    private Integer categoryId;
    private Long userId;
    private List<Long> commentIdList;
}
