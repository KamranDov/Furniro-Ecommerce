package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.CategoryDTO;
import az.crocusoft.ecommerce.exception.EntityExistsException;
import az.crocusoft.ecommerce.model.product.Category;
import az.crocusoft.ecommerce.repository.CategoryRepository;
import az.crocusoft.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDTO addCategory(Category category) {
        //checking if category with the same name exists in the db
        if (categoryRepository.existsByName(category.getName()))
            throw new EntityExistsException("Category with the name " + category.getName() + " already exists!");

        Category categoryToSave = categoryRepository.save(category);
        return modelMapper.map(categoryToSave, CategoryDTO.class);
    }
}
