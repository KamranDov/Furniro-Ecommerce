package az.crocusoft.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
