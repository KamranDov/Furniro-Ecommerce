package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.*;
import az.crocusoft.ecommerce.mapper.ProductVariationMapper;
import az.crocusoft.ecommerce.model.product.*;
import az.crocusoft.ecommerce.repository.CategoryRepository;
import az.crocusoft.ecommerce.repository.FurnitureDesignationRepository;
import az.crocusoft.ecommerce.repository.ProductRepository;
import az.crocusoft.ecommerce.repository.TagRepository;
import az.crocusoft.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final ProductVariationMapper productVariationMapper;
    private final ModelMapper modelMapper;

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

    @Override
    public List<ProductResponse> getAllPublishedProducts() {
        return productRepository.findAllByIsPublishedTrue();
    }

    @Override
    public SingleProductResponse getProductById(Long id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product is currently not available"));
        SingleProductResponse productResponse = new SingleProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setTitle(product.getTitle());
        productResponse.setCategoryName(product.getCategory().getName());
        productResponse.setTags(product.getTags().stream().map(Tag::getName).toList());
        productResponse.setProductReviews(product.getReviews()
                .stream()
                .map(review -> {
                    ReviewDTO reviewDTO = new ReviewDTO();
                    modelMapper.map(review, reviewDTO);
                    return reviewDTO;
                })
                .toList());
        productResponse.setRating(product.getProductRating());
        productResponse.setDescription(product.getDescription());
        productResponse.setLongDescription(product.getLongDescription());
        productResponse.setProductVariations(product.getVariations()
                .stream()
                .map(variation -> {
                    ProductVariationDTO productVariationDTO = productVariationMapper.toProductVariationDTO(variation);
                    return productVariationDTO;
                })
                .toList());
        System.out.println("asdasdasd");
        System.out.println(product.getTags().size());

        return productResponse;
    }


}
