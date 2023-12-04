package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.cart.AddToCartDto;
import az.crocusoft.ecommerce.dto.cart.CartDto;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.service.AuthenticationService;
import az.crocusoft.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody AddToCartDto addToCartDto) {

        User signedInUser = authenticationService.getSignedInUser();
        System.out.println(signedInUser.getEmail());
        cartService.addToCart(addToCartDto, signedInUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/cart-items")
    public ResponseEntity<CartDto> getCartItems() {
        User signedInUser = authenticationService.getSignedInUser();
        CartDto cartDto = cartService.listCartItems(signedInUser);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("delete/{cartItemId}")
    public ResponseEntity<Void> deleteCartItems(@PathVariable(name = "cartItemId") Long cartItemId) {

        User signedInUser = authenticationService.getSignedInUser();
        cartService.deleteCartItem(cartItemId, signedInUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
