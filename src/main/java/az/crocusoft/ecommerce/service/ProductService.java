package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.dto.request.ProductVariationRequest;
import az.crocusoft.ecommerce.dto.response.ProductResponse;
import az.crocusoft.ecommerce.dto.response.SingleProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(ProductRequest productRequest, MultipartFile image) throws IOException;
    void addVariationToProduct(Long productId, ProductVariationRequest addProductDTO, List<MultipartFile> images) throws IOException;
    List<ProductResponse> getAllPublishedProducts();
    SingleProductResponse getProductById(Long id);

}
