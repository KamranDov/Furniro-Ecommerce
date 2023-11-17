package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.*;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(
            AddProductDTO addProductDTO,
            @RequestParam("image") MultipartFile image) throws IOException {

        Product product = productService.addProduct(addProductDTO, image);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleProductResponse> getProductById(@PathVariable Long id) {
        SingleProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/public")
    public ResponseEntity<List<ProductResponse>> getPublishedProducts() {
        List<ProductResponse> publishedProducts = productService.getAllPublishedProducts();
        return ResponseEntity.ok(publishedProducts);
    }



}
