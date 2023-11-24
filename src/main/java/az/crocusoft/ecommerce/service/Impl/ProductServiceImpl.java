package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.ProductVariationDTO;
import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.dto.request.ProductVariationRequest;
import az.crocusoft.ecommerce.dto.response.ProductResponse;
import az.crocusoft.ecommerce.dto.response.SingleProductResponse;
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


    @Override
    public void addProduct(ProductRequest productRequest,
                           MultipartFile image) throws IOException {
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
            Category category = categoryRepository
                    .findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException
                            ("Category not found with id: " + productRequest.getCategoryId()));
            product.setCategory(category);
        }

        if (productRequest.getFurnitureDesignationIds() != null && !productRequest.getFurnitureDesignationIds().isEmpty()) {
            Set<FurnitureDesignation> furnitureDesignations = new HashSet<>();
            for (Long id : productRequest.getFurnitureDesignationIds()) {
                FurnitureDesignation fd = furnitureDesignationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException
                                ("FurnitureDesignation not found with id: " + id));
                furnitureDesignations.add(fd);
            }
            product.setFurnitureDesignations(furnitureDesignations);
        }
        productRepository.save(product);
    }

    public void addVariationToProduct(Long productId,
                                      ProductVariationRequest productVariationRequest,
                                      List<MultipartFile> images) throws IOException {
        Product product = findProductById(productId);

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
                .map(this::convertToProductResponse)
                .toList();
    }

    @Override
    public SingleProductResponse getProductById(Long id) {
        Product product = findProductById(id);
        SingleProductResponse singleProductResponse = convertToSingleProductResponse(product);
        return singleProductResponse;
    }

    private SingleProductResponse convertToSingleProductResponse(Product product) {
        SingleProductResponse productResponse = SingleProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .title(product.getTitle())
                .categoryName(product.getCategory().getName())
                .tags(product.getTags()
                        .stream()
                        .map(Tag::getName)
                        .toList())
                .reviewCount(product.getReviews().size())
                .rating(getProductRating(product))
                .description(product.getDescription())
                .longDescription(product.getLongDescription())
                .productVariations(product.getVariations()
                        .stream()
                        .map(this::toProductVariationDTO)
                        .toList())
                .build();
        return productResponse;
    }

    public Double getProductPrice(Product product) {
        List<ProductVariation> variations = product.getVariations();
        if (variations == null || variations.isEmpty())
            return null;
        return variations
                .stream()
                .mapToDouble(ProductVariation::getPrice)
                .min()
                .getAsDouble();
    }

    public Double getProductRating(Product product) {
        List<Review> reviews = product.getReviews();
        if (reviews == null || reviews.isEmpty())
            return null;
        return reviews
                .stream()
                .mapToDouble(Review::getRating)
                .sum() / reviews.size();
    }

    public Double getProductSpecialPrice(Product product) {
        List<ProductVariation> variations = product.getVariations();
        if (variations == null || variations.isEmpty())
            return null;
        return variations
                .stream()
                .mapToDouble(this::getProductVariationSpecialPrice)
                .min()
                .getAsDouble();
    }

    public Double getProductDiscount(Product product) {
        List<ProductVariation> variations = product.getVariations();
        if (variations == null || variations.isEmpty())
            return null;
        return variations
                .stream()
                .mapToDouble(ProductVariation::getDiscount)
                .max()
                .getAsDouble();
    }

    public Boolean isProductDiscounted(Product product) {
        List<ProductVariation> variations = product.getVariations();
        if (variations == null || variations.isEmpty())
            return false;
        return variations
                .stream()
                .anyMatch(variation -> variation.getDiscount() != null && variation.getDiscount() > 0);
    }

    private Double getProductVariationSpecialPrice(ProductVariation variation){
        Double discount = variation.getDiscount();
        Double price = variation.getPrice();
        if (discount == null || discount == 0)
            return price;
        return price - (price * discount / 100);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product is currently not available"));
    }


    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .title(product.getTitle())
                .isNew(product.isNew())
                .price(getProductPrice(product))
                .discount(getProductDiscount(product))
                .discountPrice(getProductSpecialPrice(product))
                .imageURL(product.getMainImage().getImageUrl())
                .build();
        return productResponse;
    }

    private ProductVariationDTO toProductVariationDTO(ProductVariation variation) {
        ProductVariationDTO productVariationDTO = new ProductVariationDTO();
        productVariationDTO.setId(variation.getId());
        productVariationDTO.setPrice(variation.getPrice());
        productVariationDTO.setDiscount(variation.getDiscount());
        productVariationDTO.setQuantity(variation.getStockQuantity());
        productVariationDTO.setSpecialPrice(getProductVariationSpecialPrice(variation));
        productVariationDTO.setColor(variation.getColor());
        productVariationDTO.setSize(variation.getSize());
        variation.getImages().forEach(image -> productVariationDTO.getImageUrls().add(image.getImageUrl()));
        return productVariationDTO;
    }


}
