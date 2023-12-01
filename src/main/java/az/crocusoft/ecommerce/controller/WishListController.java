package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import az.crocusoft.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/wishlist")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    @GetMapping("/list/{id}")
    public List<WishListDTO> getWishListByUserId(@PathVariable("id") Long userId){
        return wishListService.getWishListByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<WishListDTO> add(@RequestBody WishListDTO wishListDTO){
        wishListService.add(wishListDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id")Long productId){
        wishListService.delete(productId);
    }


}
