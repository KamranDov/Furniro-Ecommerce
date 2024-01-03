package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.dto.request.ProductVariationRequest;
import az.crocusoft.ecommerce.dto.response.ProductPageResponse;
import az.crocusoft.ecommerce.dto.response.ProductResponse;
import az.crocusoft.ecommerce.dto.response.SingleProductResponse;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(ProductRequest productRequest, MultipartFile image) throws IOException;

    void addVariationToProduct(Long productId, ProductVariationRequest addProductDTO, List<MultipartFile> images) throws IOException;

    SingleProductResponse getProductById(Long id);

    Product findProductById(Long id);

    ProductPageResponse getAllPublishedProducts(String keyword, Long designationId,
                                                List<Long> categoryId,
                                                int pageNumber, int pageSize,
                                                String sortBy, String sortOrder);
    public void deleteProduct(Long id);

    ProductVariation findById(Long productVariationId);
}
