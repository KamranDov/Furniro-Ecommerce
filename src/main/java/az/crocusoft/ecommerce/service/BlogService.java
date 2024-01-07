package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.*;
import az.crocusoft.ecommerce.exception.CustomException;
import az.crocusoft.ecommerce.model.Blog;
import az.crocusoft.ecommerce.model.BlogCategory;
import az.crocusoft.ecommerce.model.product.Image;
import az.crocusoft.ecommerce.repository.BlogCategoryRepository;
import az.crocusoft.ecommerce.repository.BlogRepository;
import az.crocusoft.ecommerce.service.Impl.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogCategoryService categoryService;
    private final ImageService imageService;
    private final BlogCategoryRepository blogCategoryRepository;
    private final FileService fileService;
    private static final String BLOG_IMAGES_FOLDER_NAME = "Blog-images";



    @Value("${file.upload-dir}")
    String uploadPath;

    public void creatBlog(BlogDto blogDto, MultipartFile image) throws Exception {
        BlogCategory category = categoryService.getCategoryById(blogDto.getCategoryId());
        Blog blog = new Blog();

        String uploadedImageURL = imageService.uploadImage(image, BLOG_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);

        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setCategory(category);
        blog.setDate(new Date());
        blog.setImageName(uploadedImage);

        blogRepository.save(blog);
    }


    @Transactional
    public void updateBlog(BlogUpdateRequest newBlog, Long blogId, MultipartFile newImage) throws IOException {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException("Blog not found"));

        String uploadedImageURL;
        if (newImage != null) {
            uploadedImageURL = imageService.uploadImage(newImage, BLOG_IMAGES_FOLDER_NAME);
        } else {
            uploadedImageURL = blog.getImageName().getImageUrl();
        }
        Image uploadedImage = new Image(uploadedImageURL);
        blog.setImageName(uploadedImage);

        BlogCategory category = categoryService.getCategoryById(newBlog.getCategoryId());

        blog.setTitle(newBlog.getTitle());
        blog.setContent(newBlog.getContent());
        blog.setCategory(category);
        blog.setDate(new Date());
        blogRepository.save(blog);
    }


    @Transactional
    public ResponseEntity deleteBlogById(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException("Blog not found with id :" + blogId));

        if (blog.getImageName() != null) {
            imageService.delete(
                    Paths.get(uploadPath).normalize().toAbsolutePath()
                            + blog.getImageName().getImageUrl().substring(7)
            );
        } else {
            throw new CustomException("Something went wrong");
        }
        blogRepository.delete(blog);
        return ResponseEntity.ok(blog);
    }

    public BlogResponseDto searchBlogsByTitleAndCategory(String title, Integer categoryId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Blog> blogPages;

        if (categoryId != null) {
            BlogCategory category = blogCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

            blogPages = blogRepository.findByTitleContainingIgnoreCaseAndCategory(title, category, pageable);
        } else {
            blogPages = blogRepository.findByTitleContainingIgnoreCase(title, pageable);
        }

        List<Blog> blogs = blogPages.getContent();

        List<BlogMainDto> blogDtoList = blogs.stream()
                .map(this::generateResponse)
                .collect(Collectors.toList());

        return new BlogResponseDto(blogDtoList, blogPages.getNumber(),
                blogPages.getTotalPages(), blogPages.getTotalElements(), blogPages.isLast());
    }


    public BlogResponseDto searchBlogsByTitle(String title, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Blog> blogPages = blogRepository.findByTitleContainingIgnoreCase(title, pageable);

        List<Blog> blogs = blogPages.getContent();
        List<BlogMainDto> blogDtoList = blogs.stream()
                .map(this::generateResponse)
                .collect(Collectors.toList());

        return new BlogResponseDto(blogDtoList, blogPages.getNumber(),
                blogPages.getTotalPages(), blogPages.getTotalElements(), blogPages.isLast());
    }



    public List<Map<String, Integer>> countBlogsByCategory() {
        return blogRepository.countBlogsPerCategory();
    }



    public List<BlogRecentDto> getRecentPosts() {
        List<Blog> recentBlogs = blogRepository.findTop6ByOrderByDateDesc();






         return recentBlogs.stream()
                .map(this::generateRecentResponse)
                .collect(Collectors.toList());
    }


    public BlogMainDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Blog not found with id :" + id));
        BlogMainDto blogMainDto = generateResponse(blog);
        return blogMainDto;
    }

    public List<BlogMainDto> getBlogMainDtoByCategoryId(Integer categoryId) {
        List<Blog> blogs = blogRepository.findByCategoryCid(categoryId);
        List<BlogMainDto> blogMainDtos = new ArrayList<>();

        for (Blog blog : blogs) {
            BlogMainDto blogMainDto = generateResponse(blog);
            blogMainDtos.add(blogMainDto);
        }

        return blogMainDtos;
    }

    private BlogMainDto generateResponse(Blog blog) {

        BlogMainDto blogMainDto = new BlogMainDto();
        blogMainDto.setPid(blog.getPid());
        blogMainDto.setTitle(blog.getTitle());
        blogMainDto.setContent(blog.getContent());
        blogMainDto.setDate(blog.getDate());
        blogMainDto.setCategoryId(blog.getCategory().getCid());
        blogMainDto.setName(blog.getCategory().getName());

        if (blog.getImageName() != null) {
            blogMainDto.setImageUrl(fileService.getFullImagePath(blog.getImageName().getImageUrl()));
        }

        return blogMainDto;
    }

    private BlogRecentDto generateRecentResponse(Blog blog) {

        BlogRecentDto blogMainDto = new BlogRecentDto();
        blogMainDto.setPid(blog.getPid());
        blogMainDto.setTitle(blog.getTitle());

        blogMainDto.setDate(blog.getDate());
        blogMainDto.setCategoryId(blog.getCategory().getCid());

        if (blog.getImageName() != null) {
            blogMainDto.setImageUrl(fileService.getFullImagePath(blog.getImageName().getImageUrl()));
        }

        return blogMainDto;
    }


    public Integer countBlogsByCategory(Integer categoryId) {
        categoryService.getCategoryById(categoryId);
        return blogRepository.countByCategoryId(categoryId);
    }


}
