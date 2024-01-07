package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.cart.AddToCartDto;
import az.crocusoft.ecommerce.dto.cart.CartDto;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.service.AuthenticationService;
import az.crocusoft.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestBody AddToCartDto addToCartDto) {

        User signedInUser = authenticationService.getSignedInUser();
        System.out.println(signedInUser.getEmail());
        cartService.addToCart(addToCartDto, signedInUser);
        Map<String, String> cartObject = new HashMap<>();
        cartObject.put("message", "Product added successfully.");
        return new ResponseEntity<>(cartObject, CREATED);
    }

    @GetMapping("/cart-items")//public
    public ResponseEntity<CartDto> getCartItems() {
        User signedInUser = authenticationService.getSignedInUser();
        CartDto cartDto = cartService.listCartItems(signedInUser);
        return new ResponseEntity<>(cartDto, OK);
    }

    @DeleteMapping("delete/{cartItemId}")
    public ResponseEntity<String> deleteCartItems(@PathVariable(name = "cartItemId") Long cartItemId) {

        User signedInUser = authenticationService.getSignedInUser();
        cartService.deleteCartItem(cartItemId, signedInUser);
        return new ResponseEntity<>("Cart item successfully deleted.", NO_CONTENT);
    }
}
