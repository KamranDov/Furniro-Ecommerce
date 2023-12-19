package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.exception.CustomException;
import az.crocusoft.ecommerce.model.BlogCategory;
import az.crocusoft.ecommerce.repository.BlogCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCategoryService {


    private final BlogCategoryRepository categoryRepository;

    public BlogCategory createCategory(BlogCategory category) {
        return categoryRepository.save(category);
    }


    public BlogCategory updateCategory(BlogCategory category, Integer id) {
        BlogCategory foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException("Category not found with id :" + id));
        foundCategory.setName(category.getName());
        foundCategory.setDescription(category.getDescription());
        return categoryRepository.save(foundCategory);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException("Category not found with id :" + id));
        categoryRepository.deleteById(id);
    }


    public BlogCategory getCategoryById(Integer id) {
        BlogCategory foundCategory = categoryRepository
                .findById(id).orElseThrow(() -> new CustomException("Category not found with id :" + id));
        return foundCategory;
    }

    public BlogCategory getCategoryByName(String name) {
        BlogCategory foundCategory = categoryRepository
                .findCategoryByName(name).orElseThrow(() -> new CustomException("Category not found with name :" + name));
        return foundCategory;
    }


    public List<BlogCategory> getAllCategories() {
        List<BlogCategory> allCategories = categoryRepository.findAll();
        if (allCategories.size() == 0)
            throw new CustomException("No Category found");
        return allCategories;
    }
}
