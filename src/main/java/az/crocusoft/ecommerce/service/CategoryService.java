package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.request.CategoryRequest;
import az.crocusoft.ecommerce.dto.response.CategoryResponse;

public interface CategoryService {
    void addCategory(CategoryRequest category);
    CategoryResponse getCategory(Long categoryId);



}
