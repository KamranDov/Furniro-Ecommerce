package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.exception.CustomException;
import az.crocusoft.ecommerce.model.Category;
import az.crocusoft.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }


    public Category updateCategory(Category category, Integer id) {
        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(()->new CustomException("Category not found with id :"+id, HttpStatus.NOT_FOUND));
        foundCategory.setName(category.getName());
        foundCategory.setDescription(category.getDescription());
        return categoryRepository.save(foundCategory);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.findById(id)
                .orElseThrow(()->new CustomException("Category not found with id :"+id,HttpStatus.NOT_FOUND));
        categoryRepository.deleteById(id);
    }



    public Category getCategoryById(Integer id) {
        Category foundCategory = categoryRepository
                .findById(id).orElseThrow(()->new CustomException("Category not found with id :"+id,HttpStatus.NOT_FOUND));
        return foundCategory;
    }
     public Category getCategoryByName(String name) {
        Category foundCategory = categoryRepository
                .findCategoryByName(name).orElseThrow(()->new CustomException("Category not found with name :"+name,HttpStatus.NOT_FOUND));
        return foundCategory;
    }



    public List<Category> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        if(allCategories.size()==0)
            throw new CustomException("No Category found",HttpStatus.NOT_FOUND);
        return allCategories;
    }
}
