package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.WishListDTO;
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



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/{id}")
    public ResponseEntity<List<WishListDTO>> getWishListByUserId(@PathVariable("id") Long userId){
        return ResponseEntity.ok(wishListService.getWishListByUserId(userId));

    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody WishListDTO wishListDTO){
        wishListService.add(wishListDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody WishListDTO wishListDTO){
        wishListService.delete(wishListDTO);
        return ResponseEntity.ok().build();
    }


}
