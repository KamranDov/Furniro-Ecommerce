package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddCategoryDTO;
import az.crocusoft.ecommerce.dto.CategoryDTO;
import az.crocusoft.ecommerce.model.product.Category;
import org.springframework.stereotype.Service;

public interface CategoryService {
    CategoryDTO addCategory(AddCategoryDTO category);


}
