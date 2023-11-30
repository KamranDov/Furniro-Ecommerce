package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.request.CategoryRequest;
import az.crocusoft.ecommerce.dto.response.CategoryResponse;
import az.crocusoft.ecommerce.exception.EntityExistsException;
import az.crocusoft.ecommerce.model.product.Category;
import az.crocusoft.ecommerce.repository.CategoryRepository;
import az.crocusoft.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public void addCategory(CategoryRequest category) {
        //checking if category with the same name exists in the db
        if (categoryRepository.existsByName(category.getName()))
            throw new EntityExistsException("Category with the name " + category.getName() + " already exists!");
        Category categoryToSave = new Category();
        categoryToSave.setName(category.getName());
        categoryRepository.save(categoryToSave);

    }

    @Override
    public CategoryResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityExistsException("Category not found!"));
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categories = categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
        return categories;
    }
}
