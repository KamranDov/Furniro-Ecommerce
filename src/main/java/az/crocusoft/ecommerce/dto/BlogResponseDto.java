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


//    private Long pid;
//    private String title;
//    private String content;
//    private String imageUrl;
//    private LocalDate date;
//    private Integer categoryId;
//    private Long userId;
//    private List<Long> commentIdList;
//
//    public BlogResponseDto(Blog post) {
//
//        this.pid = post.getPid();
//        this.title = post.getTitle();
//        this.content = post.getContent();
//        this.imageUrl = post.getImageUrl();
//        this.date = post.getDate();
//        this.categoryId = post.getCategory().getCid();
//
//
//    }

}
