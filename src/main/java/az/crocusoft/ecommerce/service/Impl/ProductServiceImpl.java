package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.dto.request.ProductVariationRequest;
import az.crocusoft.ecommerce.dto.response.ProductResponse;
import az.crocusoft.ecommerce.dto.response.SingleProductResponse;
import az.crocusoft.ecommerce.mapper.ProductMapper;
import az.crocusoft.ecommerce.model.product.*;
import az.crocusoft.ecommerce.repository.*;
import az.crocusoft.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final FurnitureDesignationRepository furnitureDesignationRepository;
    private final TagRepository tagRepository;
    private final FileService fileService;
    private final ProductVariationRepository productVariationRepository;
    private static final String PRODUCT_IMAGES_FOLDER_NAME = "Product-images";

    private final ProductMapper productMapper;

    @Override
    public void addProduct(ProductRequest productRequest, MultipartFile image) throws IOException {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setTitle(productRequest.getTitle());
        product.setPublished(productRequest.isPublished());
        product.setNew(productRequest.isNew());
        product.setDescription(productRequest.getDescription());
        product.setLongDescription(productRequest.getLongDescription());

        String uploadedImageURL = fileService.uploadImage(image, PRODUCT_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);
        product.setMainImage(uploadedImage);

        Set<Tag> tags = new HashSet<>();
        for (String tagName : productRequest.getTagNames().split(" ")) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElse(new Tag(tagName));
            tags.add(tag);
        }
        product.setTags(tags);

        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productRequest.getCategoryId()));
            product.setCategory(category);
        }

        if (productRequest.getFurnitureDesignationIds() != null && !productRequest.getFurnitureDesignationIds().isEmpty()) {
            Set<FurnitureDesignation> furnitureDesignations = new HashSet<>();
            for (Long id : productRequest.getFurnitureDesignationIds()) {
                FurnitureDesignation fd = furnitureDesignationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("FurnitureDesignation not found with id: " + id));
                furnitureDesignations.add(fd);
            }
            product.setFurnitureDesignations(furnitureDesignations);
        }
        productRepository.save(product);
    }

    public void addVariationToProduct(Long productId, ProductVariationRequest productVariationRequest, List<MultipartFile> images) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found" ));
        ProductVariation productVariation = ProductVariation.builder()
                .product(product)
                .sku(productVariationRequest.getSku())
                .color(productVariationRequest.getColor())
                .size(productVariationRequest.getSize())
                .price(productVariationRequest.getPrice())
                .discount(productVariationRequest.getDiscount())
                .stockQuantity(productVariationRequest.getStockQuantity())
                .build();
        System.out.println(productVariationRequest.getPrice());
        Set<Image> productVarImages = new HashSet<>();

        for (MultipartFile image : images) {
            String uploadedImageURL = fileService.uploadImage(image, PRODUCT_IMAGES_FOLDER_NAME);
            Image uploadedImage = new Image(uploadedImageURL);
            productVarImages.add(uploadedImage);
        }
        productVariation.setImages(productVarImages);
        productVariationRepository.save(productVariation);
    }


    @Override
    public List<ProductResponse> getAllPublishedProducts() {
        return productRepository.findAllByIsPublishedTrue()
                .stream()
                .map(productMapper::convertToProductResponse)
                .toList();
    }

    @Override
    public SingleProductResponse getProductById(Long id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product is currently not available"));
        SingleProductResponse productResponse = SingleProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .title(product.getTitle())
                .categoryName(product.getCategory().getName())
                .tags(product.getTags().stream().map(Tag::getName).toList())
                .reviewCount(product.getReviews().size())
                .rating(product.getProductRating())
                .description(product.getDescription())
                .longDescription(product.getLongDescription())
                .productVariations(product.getVariations()
                        .stream()
                        .map(productMapper::toProductVariationDTO)
                        .toList())
                .build();
        return productResponse;
    }


}
