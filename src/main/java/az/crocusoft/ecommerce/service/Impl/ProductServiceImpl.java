package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.AddProductDTO;
import az.crocusoft.ecommerce.model.product.*;
import az.crocusoft.ecommerce.repository.CategoryRepository;
import az.crocusoft.ecommerce.repository.FurnitureDesignationRepository;
import az.crocusoft.ecommerce.repository.ProductRepository;
import az.crocusoft.ecommerce.repository.TagRepository;
import az.crocusoft.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private FurnitureDesignationRepository furnitureDesignationRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private FileService fileService;
    private static final String PRODUCT_IMAGES_FOLDER_NAME = "Product-images";

    @Override
    public Product addProduct(AddProductDTO addProductDTO, MultipartFile image) throws IOException {
        Product product = new Product();
        product.setName(addProductDTO.getName());
        product.setTitle(addProductDTO.getTitle());
        product.setPublished(addProductDTO.isPublished());
        product.setNew(addProductDTO.isNew());
        product.setDescription(addProductDTO.getDescription());
        product.setLongDescription(addProductDTO.getLongDescription());

        String uploadedImageURL = fileService.uploadImage(image, PRODUCT_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);
        product.setMainImage(uploadedImage);

        Set<Tag> tags = new HashSet<>();
        for (String tagName : addProductDTO.getTagNames().split(" ")) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElse(new Tag(tagName));
            tags.add(tag);
        }
        product.setTags(tags);

        if (addProductDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(addProductDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + addProductDTO.getCategoryId()));
            product.setCategory(category);
        }

        if (addProductDTO.getFurnitureDesignationIds() != null && !addProductDTO.getFurnitureDesignationIds().isEmpty()) {
            Set<FurnitureDesignation> furnitureDesignations = new HashSet<>();
            for (Long id : addProductDTO.getFurnitureDesignationIds()) {
                FurnitureDesignation fd = furnitureDesignationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("FurnitureDesignation not found with id: " + id));
                furnitureDesignations.add(fd);
            }
            product.setFurnitureDesignations(furnitureDesignations);
        }

        Product savedProduct = productRepository.save(product);


        return savedProduct;
    }
}
