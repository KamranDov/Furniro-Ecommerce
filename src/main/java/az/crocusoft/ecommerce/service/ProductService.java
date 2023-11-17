package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddProductDTO;
import az.crocusoft.ecommerce.model.product.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    Product addProduct(AddProductDTO addProductDTO, MultipartFile image) throws IOException;
}
