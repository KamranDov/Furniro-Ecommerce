package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.request.CategoryRequest;
import az.crocusoft.ecommerce.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest category);
    CategoryResponse getCategory(Long categoryId);
    List<CategoryResponse> getAllCategories();

}
