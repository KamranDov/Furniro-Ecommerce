package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.dto.request.ProductVariationRequest;
import az.crocusoft.ecommerce.dto.response.ProductResponse;
import az.crocusoft.ecommerce.dto.response.SingleProductResponse;
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
            ProductRequest productRequest,
            @RequestParam("image") MultipartFile image) throws IOException {
       productService.addProduct(productRequest, image);
    }

    @PostMapping(path = "/{productId}/variations", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductVariation(
            @PathVariable Long productId,
            ProductVariationRequest productVariationRequest,
            @RequestParam("images") List<MultipartFile> images) throws IOException {
        productService.addVariationToProduct(productId, productVariationRequest, images);
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
