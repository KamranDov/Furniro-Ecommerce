package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddProductDTO;
import az.crocusoft.ecommerce.dto.ProductResponse;
import az.crocusoft.ecommerce.dto.SingleProductResponse;
import az.crocusoft.ecommerce.model.product.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product addProduct(AddProductDTO addProductDTO, MultipartFile image) throws IOException;
    List<ProductResponse> getAllPublishedProducts();
    SingleProductResponse getProductById(Long id);
}
