package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.CategoryDTO;
import az.crocusoft.ecommerce.model.product.Category;
import az.crocusoft.ecommerce.repository.CategoryRepository;
import az.crocusoft.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody Category category) {
        CategoryDTO categoryDTO = categoryService.addCategory(category);

        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.CREATED);
    }

    

}
