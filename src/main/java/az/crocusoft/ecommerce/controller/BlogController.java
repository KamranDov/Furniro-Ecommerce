package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.constants.PaginationConstants;
import az.crocusoft.ecommerce.dto.*;
import az.crocusoft.ecommerce.model.Blog;
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
import java.util.Map;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class
BlogController {
    private final BlogService blogService;

    @GetMapping("/public/blogs")
    public ResponseEntity<BlogResponseDto> getAllBlogs(
            @RequestParam(name = "title", defaultValue = "", required = false) String title,
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "pageNumber", defaultValue = PaginationConstants.PAGE_NUMBER) Integer page,
            @RequestParam(name = "pageSize", defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE) Integer size) {

        BlogResponseDto response;

        if (categoryId != null) {
            response = blogService.searchBlogsByTitleAndCategory(title, categoryId, page, size);
        } else {
            response = blogService.searchBlogsByTitle(title, page, size);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{cid}")
    public List<BlogMainDto> getBlogsByCategory(@PathVariable("cid") Integer categoryId) {
        return blogService.getBlogMainDtoByCategoryId(categoryId);
    }
    @GetMapping("/{pid}")
    public ResponseEntity<BlogMainDto> getBlogById(@PathVariable("pid") Long blogId) {
        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    @GetMapping("/recent")
    public List<BlogRecentDto> getRecentBlogPosts() {
        return blogService.getRecentPosts();
    }

    @GetMapping("/count")
    public List<Map<String, Integer>> countBlogsByCategory() {
        return blogService.countBlogsByCategory();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> saveBlog(BlogDto blog, @RequestParam("image") MultipartFile image) throws Exception {
        blogService.creatBlog(blog, image);
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
        blogService.deleteBlogById(blogId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
