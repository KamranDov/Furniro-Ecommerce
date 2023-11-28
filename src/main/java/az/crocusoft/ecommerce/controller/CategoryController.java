package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.CategoryDto;
import az.crocusoft.ecommerce.exception.ApiResponse;
import az.crocusoft.ecommerce.mapper.ModelMapperBean;
import az.crocusoft.ecommerce.model.Category;
import az.crocusoft.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        categoryService.createCategory(category);
        return categoryDto;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategories()
                .stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/{cid}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("cid") Integer cid) {
        Category category = categoryService.getCategoryById(cid);
        return new ResponseEntity<>(modelMapper.map(category, CategoryDto.class), HttpStatus.OK);
    }

    @PutMapping("/{cid}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable("cid") Integer cid,
                                                          @Valid @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(category, cid);
        return new ResponseEntity<>(modelMapper.map(updatedCategory, CategoryDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("cid") Integer cid) {
        categoryService.deleteCategory(cid);
        return new ResponseEntity<>(new ApiResponse("Category successfully deleted with id :" + cid,
                LocalDateTime.now(), HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
    }
}