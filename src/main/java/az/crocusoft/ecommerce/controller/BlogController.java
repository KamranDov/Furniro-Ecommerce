package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.BlogDto;
import az.crocusoft.ecommerce.dto.BlogMainDto;
import az.crocusoft.ecommerce.dto.BlogResponseDto;
import az.crocusoft.ecommerce.dto.BlogUpdateRequest;
import az.crocusoft.ecommerce.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class
BlogController {
    private final BlogService blogService;


    @GetMapping
    public ResponseEntity<BlogResponseDto> getAllBlogs(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize
    ) {
        BlogResponseDto response = blogService.getAllBlogs(pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{pid}")
    public ResponseEntity<BlogMainDto> getBlogById(@PathVariable("pid") Long blogId) {
        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    @GetMapping("/recent/{months}")
    public List<BlogMainDto> getRecentBlogPosts(@PathVariable int months) {
        return blogService.getRecentPosts(months);
    }

    @GetMapping("/count/{categoryId}")
    public Integer getRecentBlogPosts(@PathVariable Integer categoryId) {
        return blogService.countBlogsByCategory(categoryId);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BlogMainDto> saveBlog(@RequestPart BlogDto blog, @RequestPart MultipartFile image) {
        blog.setImage(image);
        blogService.creatBlog(blog);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{pid}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BlogMainDto updateBlog(@RequestPart("blog") BlogUpdateRequest blog,
                                  @PathVariable("pid") Long blogId,
                                  @RequestPart("image") MultipartFile image) {
        return blogService.updateBlog(blog, blogId, image);
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity deleteBlog(@PathVariable("pid") Long blogId) {
        return ResponseEntity.ok(blogService.deleteBlogById(blogId));
    }


}
