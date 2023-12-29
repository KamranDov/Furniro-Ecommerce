package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import az.crocusoft.ecommerce.service.AuthenticationService;
import az.crocusoft.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/wishlist")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;


    @GetMapping("/list/{userid}")
    public ResponseEntity<List<WishListDTO>> getWishListByUserId(@PathVariable("userid") Long userId){
        return ResponseEntity.ok(wishListService.getWishListByUserId(userId));

    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestParam("productVariationId") Long id){
        wishListService.add(id);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("productVariationId") Long productId){
        wishListService.delete(productId);
        return ResponseEntity.ok().build();
    }


}
