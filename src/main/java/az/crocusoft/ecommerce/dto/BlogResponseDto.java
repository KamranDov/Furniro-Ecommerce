package az.crocusoft.ecommerce.dto;


import az.crocusoft.ecommerce.model.Blog;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponseDto {
    private List<BlogMainDto> blogs;
    private Integer currentPage;
    private Integer totalPage;
    private Long totalBlogs;
    private Boolean isLastPage;



}
