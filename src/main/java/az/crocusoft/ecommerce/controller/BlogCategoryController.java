package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.BlogCategoryDto;
import az.crocusoft.ecommerce.exception.ApiResponse;
import az.crocusoft.ecommerce.model.BlogCategory;
import az.crocusoft.ecommerce.service.BlogCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class BlogCategoryController {

    private final BlogCategoryService categoryService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public BlogCategoryDto createCategory(@Valid @RequestBody BlogCategoryDto categoryDto) {
        BlogCategory category = new BlogCategory();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        categoryService.createCategory(category);
        return categoryDto;
    }

    @GetMapping()
    public ResponseEntity<List<BlogCategoryDto>> getAllCategories() {
        List<BlogCategoryDto> allCategories = categoryService.getAllCategories()
                .stream().map(category -> modelMapper.map(category, BlogCategoryDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/{cid}")
    public ResponseEntity<BlogCategoryDto> getCategoryById(@PathVariable("cid") Integer cid) {
        BlogCategory category = categoryService.getCategoryById(cid);
        return new ResponseEntity<>(modelMapper.map(category, BlogCategoryDto.class), HttpStatus.OK);
    }

    @PutMapping("/{cid}")
    public ResponseEntity<BlogCategoryDto> updateCategoryById(@PathVariable("cid") Integer cid,
                                                              @Valid @RequestBody BlogCategory category) {
        BlogCategory updatedCategory = categoryService.updateCategory(category, cid);
        return new ResponseEntity<>(modelMapper.map(updatedCategory, BlogCategoryDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("cid") Integer cid) {
        categoryService.deleteCategory(cid);
        return new ResponseEntity<>(new ApiResponse("Category successfully deleted with id :" + cid,
                LocalDateTime.now(), HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
    }
}
