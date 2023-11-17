package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.AddCategoryDTO;
import az.crocusoft.ecommerce.dto.CategoryDTO;
import az.crocusoft.ecommerce.exception.EntityExistsException;
import az.crocusoft.ecommerce.model.product.Category;
import az.crocusoft.ecommerce.repository.CategoryRepository;
import az.crocusoft.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO addCategory(AddCategoryDTO category) {
        //checking if category with the same name exists in the db
        if (categoryRepository.existsByName(category.getName()))
            throw new EntityExistsException("Category with the name " + category.getName() + " already exists!");
        Category categoryToSave = new Category();
        categoryToSave.setName(category.getName());

        Category savedCategory = categoryRepository.save(categoryToSave);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
