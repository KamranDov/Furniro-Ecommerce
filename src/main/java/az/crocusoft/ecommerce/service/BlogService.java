package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.BlogDto;
import az.crocusoft.ecommerce.dto.BlogMainDto;
import az.crocusoft.ecommerce.dto.BlogResponseDto;
import az.crocusoft.ecommerce.dto.BlogUpdateRequest;
import az.crocusoft.ecommerce.exception.CustomException;
import az.crocusoft.ecommerce.model.Blog;
import az.crocusoft.ecommerce.model.Category;
import az.crocusoft.ecommerce.model.ImageUpload;
import az.crocusoft.ecommerce.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    String FILE_PATH = "C:\\Users\\Admin\\Pictures\\Screenshots\\";

    @Value("${file.downloadPath}")
    String downloadPath;

    @Value("${file.uploadPath}")
    String uploadPath;


    public BlogDto creatBlog(BlogDto blogDto) {
        Category category = categoryService.getCategoryById(blogDto.getCategoryId());
        Blog blog = new Blog();
        String imageName;
        try {
            imageName = imageService.saveFile(blogDto.getImage()).getFileName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setCategory(category);
        blog.setDate(new Date());
        blog.setImageName(imageName);
        blogRepository.save(blog);


        return blogDto;

    }


    public BlogUpdateRequest updateBlog(BlogUpdateRequest newBlog, Long blogId, MultipartFile newImage) {

        Category category = categoryService.getCategoryById(newBlog.getCategoryId());
        ImageUpload image;
        try {
            image = imageService.saveFile(newImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException("Blog not found with id :" + blogId, HttpStatus.NOT_FOUND));
        blog.setTitle(newBlog.getTitle());
        blog.setContent(newBlog.getContent());
        blog.setCategory(category);
        blog.setDate(new Date());
        blog.setImageName(image.getFileName());
        blogRepository.save(blog);

        newBlog.setDate(new Date());
        newBlog.setImageName(blog.getImageName());

        return newBlog;
    }


    public ResponseEntity deleteBlogById(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException("Blog not found with id :" + blogId, HttpStatus.NOT_FOUND));


        blogRepository.delete(blog);
        return ResponseEntity.ok(blog);
    }

    public BlogResponseDto getAllBlogs(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Blog> blogPages = blogRepository.findAll(pageable);

        List<Blog> blogs = blogPages.getContent();

        List<BlogMainDto> blogDtoList = blogs.stream()
                .map(blog -> generateResponse(blog))
                .collect(Collectors.toList());

        BlogResponseDto response = new BlogResponseDto();
        response.setBlogs(blogDtoList);
        response.setCurrentPage(blogPages.getNumber());
        response.setIsLastPage(blogPages.isLast());
        response.setTotalBlogs(blogPages.getTotalElements());
        response.setTotalPage(blogPages.getTotalPages());
        return response;
    }

    public List<BlogMainDto> getRecentPosts(Integer months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -months);
        Date startDate = calendar.getTime();

        List<Blog> blogs = blogRepository.findByDateGreaterThanEqual(startDate);

        List<BlogMainDto> blogDtoList = blogs.stream()
                .map(blog -> generateResponse(blog))
                .collect(Collectors.toList());

        return blogDtoList;
    }


    public BlogMainDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Blog not found with id :" + id, HttpStatus.NOT_FOUND));
        BlogMainDto blogMainDto = generateResponse(blog);
        return blogMainDto;
    }


    private BlogMainDto generateResponse(Blog blog) {
        String imageUrl = uploadPath + blog.getImageName();
        BlogMainDto blogMainDto = new BlogMainDto();
        blogMainDto.setPid(blog.getPid());
        blogMainDto.setTitle(blog.getTitle());
        blogMainDto.setContent(blog.getContent());
        blogMainDto.setDate(blog.getDate());
        blogMainDto.setCategoryId(blog.getCategory().getCid());
        blogMainDto.setImageUrl(imageUrl);
        blogMainDto.setUserId(null);
        blogMainDto.setCommentsId(null);
        return blogMainDto;

    }


}
