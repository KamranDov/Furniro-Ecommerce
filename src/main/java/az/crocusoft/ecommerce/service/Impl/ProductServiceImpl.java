package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.constants.PaginationConstants;
import az.crocusoft.ecommerce.dto.ProductVariationDTO;
import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.dto.request.ProductVariationRequest;
import az.crocusoft.ecommerce.dto.response.ProductPageResponse;
import az.crocusoft.ecommerce.dto.response.ProductResponse;
import az.crocusoft.ecommerce.dto.response.SingleProductResponse;
import az.crocusoft.ecommerce.exception.ProductNotExistsException;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.model.product.*;
import az.crocusoft.ecommerce.repository.*;
import az.crocusoft.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final FurnitureDesignationRepository furnitureDesignationRepository;
    private final TagRepository tagRepository;
    private final FileService fileService;
    private final ProductVariationRepository productVariationRepository;
    private static final String PRODUCT_IMAGES_FOLDER_NAME = "Product-images";


    @Transactional
    public void addProduct(ProductRequest productRequest,
                           MultipartFile image) throws IOException {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setTitle(productRequest.getTitle());
        product.setPublished(productRequest.isPublished());
        product.setNewProduct(productRequest.isNew());
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
                    .orElseThrow(() -> new ResourceNotFoundException
                            ("Category not found with id: " + productRequest.getCategoryId()));
            product.setCategory(category);
        }

        if (productRequest.getFurnitureDesignationIds() != null && !productRequest.getFurnitureDesignationIds().isEmpty()) {
            Set<FurnitureDesignation> furnitureDesignations = new HashSet<>();
            for (Long id : productRequest.getFurnitureDesignationIds()) {
                FurnitureDesignation fd = furnitureDesignationRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException
                                ("FurnitureDesignation not found with id: " + id));
                furnitureDesignations.add(fd);
            }
            product.setFurnitureDesignations(furnitureDesignations);
        }
        productRepository.save(product);
    }

    @Transactional
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
        Set<Image> productVarImages = new HashSet<>();

        for (MultipartFile image : images) {
            String uploadedImageURL = fileService.uploadImage(image, PRODUCT_IMAGES_FOLDER_NAME);
            Image uploadedImage = new Image(uploadedImageURL);
            productVarImages.add(uploadedImage);
        }
        productVariation.setImages(productVarImages);
        productVariationRepository.save(productVariation);
    }


    public ProductPageResponse getAllPublishedProducts(String keyword, Long designationId,
                                                       List<Long> categoryIds,
                                                       int pageNumber, int pageSize,
                                                       String sortBy, String sortOrder) {

        List<String> sortFields = Arrays.asList(PaginationConstants.fields);
        if (!sortFields.contains(sortBy.toLowerCase())) {
            sortBy = PaginationConstants.SORT_BY;
        }
        List<String> orders = Arrays.asList(PaginationConstants.orders);
        if (!orders.contains(sortOrder.toUpperCase())) {
            sortOrder = PaginationConstants.SORT_ORDER;
        }
        pageNumber = Math.max(pageNumber, Integer.parseInt(PaginationConstants.PAGE_NUMBER));
        pageSize = pageSize < 1 ? Integer.parseInt(PaginationConstants.DEFAULT_PAGE_SIZE) : pageSize;
        pageSize = Math.min(pageSize, Integer.parseInt(PaginationConstants.MAX_PAGE_SIZE));

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if (categoryIds == null || categoryIds.isEmpty() || categoryIds.contains(0L)) {
            categoryIds = categoryRepository.findAll()
                    .stream()
                    .map(Category::getId)
                    .toList();
        }

        Page<Product> allProducts = productRepository
                .findProductsWithMinPriceAndKeywordAndDesignationAndCategories(
                keyword, designationId, categoryIds, pageable
                );

        return new ProductPageResponse(allProducts.getContent()
                .stream()
                .map(this::convertToProductResponse)
                .toList(),
                allProducts.getTotalPages(),
                allProducts.getTotalElements(),
                allProducts.hasNext());
    }

    @Override
    public SingleProductResponse getProductById(Long id) {
        Product product = findProductById(id);
        return convertToSingleProductResponse(product);
    }

    private SingleProductResponse convertToSingleProductResponse(Product product) {
        return SingleProductResponse.builder()
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
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
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

    public Double getProductVariationSpecialPrice(ProductVariation variation) {
        Double discount = variation.getDiscount();
        Double price = variation.getPrice();
        if (discount == null || discount == 0)
            return price;
        return price - (price * discount / 100);
    }


    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product is currently not available"));
    }


    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .title(product.getTitle())
                .isNew(product.isNewProduct())
                .price(getProductPrice(product))
                .discount(getProductDiscount(product))
                .discountPrice(getProductSpecialPrice(product))
                .imageURL(fileService.getFullImagePath(product.getMainImage().getImageUrl()))
                .build();
        return productResponse;
    }

    private ProductVariationDTO toProductVariationDTO(ProductVariation variation) {
        ProductVariationDTO productVariationDTO = new ProductVariationDTO();
        productVariationDTO.setVariationId(variation.getProductVariationiId());
        productVariationDTO.setSku(variation.getSku());
        productVariationDTO.setPrice(variation.getPrice());
        productVariationDTO.setDiscount(variation.getDiscount());
        productVariationDTO.setQuantity(variation.getStockQuantity());
        productVariationDTO.setSpecialPrice(getProductVariationSpecialPrice(variation));
        productVariationDTO.setColor(variation.getColor());
        productVariationDTO.setSize(variation.getSize());
        variation.getImages().forEach(
                image -> productVariationDTO
                        .getImageUrls()
                        .add(fileService.getFullImagePath(image.getImageUrl()))
        );
        return productVariationDTO;
    }

    @Override
    public ProductVariation findById( Long productVariationId) throws ProductNotExistsException{
        Optional<ProductVariation> variationOptional = productVariationRepository.findById(productVariationId);
        if (variationOptional.isEmpty()) {
            throw new ProductNotExistsException("product id is invalid" + productVariationId);
        }
        return variationOptional.get();

    }

}

