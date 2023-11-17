package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.AddFurnitureDesignationDTO;
import az.crocusoft.ecommerce.dto.AddProductDTO;
import az.crocusoft.ecommerce.dto.FurnitureDesignationDTO;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(
            @ModelAttribute AddProductDTO addProductDTO,
            @RequestParam("image") MultipartFile image) throws IOException {

        Product product = productService.addProduct(addProductDTO, image);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

}
