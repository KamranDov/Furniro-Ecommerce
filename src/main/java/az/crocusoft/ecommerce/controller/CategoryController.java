package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.AddCategoryDTO;
import az.crocusoft.ecommerce.dto.CategoryDTO;
import az.crocusoft.ecommerce.model.product.Category;
import az.crocusoft.ecommerce.repository.CategoryRepository;
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
    public void addCategory(@Valid @RequestBody AddCategoryDTO category) {
          categoryService.addCategory(category);
    }
}
