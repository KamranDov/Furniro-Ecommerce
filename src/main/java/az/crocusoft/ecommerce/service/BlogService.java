package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.*;
import az.crocusoft.ecommerce.exception.CustomException;
import az.crocusoft.ecommerce.model.Blog;
import az.crocusoft.ecommerce.model.BlogCategory;
import az.crocusoft.ecommerce.model.ImageUpload;
import az.crocusoft.ecommerce.model.product.Image;
import az.crocusoft.ecommerce.repository.BlogRepository;
import az.crocusoft.ecommerce.service.Impl.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogCategoryService categoryService;
    private final ImageService imageService;
    private final AuthenticationService authenticationService;
    private final FileService fileService;
    private static final String BLOG_IMAGES_FOLDER_NAME = "Blog-images";



    @Value("${file.upload-dir}")
    String uploadPath;

    public BlogMainDto creatBlog(BlogDto blogDto) throws Exception {
        BlogCategory category = categoryService.getCategoryById(blogDto.getCategoryId());
        Blog blog = new Blog();
        Long signedInUserId = authenticationService.getSignedInUser().getId();

        MultipartFile image = blogDto.getImage();

        String uploadedImageURL = imageService.uploadImage(image, BLOG_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);


        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setCategory(category);
        blog.setDate(new Date());
        blog.setImageName(uploadedImage);

        blogRepository.save(blog);

        return generateResponse(blog);

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


    public ResponseEntity deleteBlogById(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException("Blog not found with id :" + blogId));

        imageService.delete(String.valueOf(blog.getImageName()));
        blogRepository.delete(blog);
        return ResponseEntity.ok(blog);
    }

    public BlogResponseDto getAllBlogs(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Blog> blogPages = blogRepository.findAll(pageable);

        List<Blog> blogs = blogPages.getContent();

        List<BlogMainDto> blogDtoList = blogs.stream()
                .map(this::generateResponse)
                .collect(toList());

        BlogResponseDto response = new BlogResponseDto();
        response.setBlogs(blogDtoList);
        response.setCurrentPage(blogPages.getNumber());
        response.setIsLastPage(blogPages.isLast());
        response.setTotalBlogs(blogPages.getTotalElements());
        response.setTotalPage(blogPages.getTotalPages());
        return response;
    }

    public List<BlogRecentDto> getRecentPosts(Integer months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -months);
        Date startDate = calendar.getTime();

        List<Blog> blogs = blogRepository.findByDateGreaterThanEqual(startDate);

        List<BlogRecentDto> blogDtoList = blogs.stream()
                .map(blog -> generateRecentResponse(blog))
                .collect(toList());

        return blogDtoList;
    }


    public BlogMainDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Blog not found with id :" + id));
        BlogMainDto blogMainDto = generateResponse(blog);
        return blogMainDto;
    }


    private BlogMainDto generateResponse(Blog blog) {

        BlogMainDto blogMainDto = new BlogMainDto();
        blogMainDto.setPid(blog.getPid());
        blogMainDto.setTitle(blog.getTitle());
        blogMainDto.setContent(blog.getContent());
        blogMainDto.setDate(blog.getDate());
        blogMainDto.setCategoryId(blog.getCategory().getCid());

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
