package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.*;
import az.crocusoft.ecommerce.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class
BlogController {
    private final BlogService blogService;


    @GetMapping
    public ResponseEntity<BlogResponseDto> getAllBlogs(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        BlogResponseDto response = blogService.getAllBlogs(pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{pid}")
    public ResponseEntity<BlogMainDto> getBlogById(@PathVariable("pid") Long blogId) {
        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    @GetMapping("/recent/{months}")
    public List<BlogRecentDto> getRecentBlogPosts(@PathVariable int months) {
        return blogService.getRecentPosts(months);
    }


    @GetMapping("/count/{categoryId}")
    public Integer getRecentBlogPosts(@PathVariable Integer categoryId) {
        return blogService.countBlogsByCategory(categoryId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<BlogMainDto> saveBlog(BlogDto blog, @RequestParam("image") MultipartFile image) throws Exception {
        blog.setImage(image);
        blogService.creatBlog(blog);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{pid}", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> updateBlog(@PathVariable("pid") Long blogId,
                                  BlogUpdateRequest blog,
                                  @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        blogService.updateBlog(blog, blogId, image);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{pid}")
    public ResponseEntity deleteBlog(@PathVariable("pid") Long blogId) {
        return ResponseEntity.ok(blogService.deleteBlogById(blogId));
    }


}
