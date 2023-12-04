package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.request.CategoryRequest;
import az.crocusoft.ecommerce.dto.response.CategoryResponse;
import az.crocusoft.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategory(@Valid @RequestBody CategoryRequest category) {
          categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }
//
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

}
