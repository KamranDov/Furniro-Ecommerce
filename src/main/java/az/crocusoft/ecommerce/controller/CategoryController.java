package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.request.CategoryRequest;
import az.crocusoft.ecommerce.dto.response.CategoryResponse;
import az.crocusoft.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

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
}
