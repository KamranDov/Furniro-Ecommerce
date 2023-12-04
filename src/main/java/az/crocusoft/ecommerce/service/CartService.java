package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.cart.AddToCartDto;
import az.crocusoft.ecommerce.dto.cart.CartDto;
import az.crocusoft.ecommerce.dto.cart.CartItemDto;
import az.crocusoft.ecommerce.exception.CartItemNotFoundException;
import az.crocusoft.ecommerce.exception.CartItemOwnershipException;
import az.crocusoft.ecommerce.model.Cart;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, User user) {
        ProductVariation productVariation = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProductVariation(productVariation);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(LocalDate.now());
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {

        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalPrice = 0;
        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalPrice += cartItemDto.getQuantity() * cart.getProductVariation().getPrice();
            cartItems.add(cartItemDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(totalPrice);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }










    public void deleteCartItem(Long cartItemId, User user) {

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        if (optionalCart.isEmpty()) {
            throw new CartItemNotFoundException("cart item id is invalid: " + cartItemId);
        }
        Cart cart = optionalCart.get();
        if (cart.getUser() != user) {
            throw new CartItemOwnershipException("cart item does not belong to user: " + cartItemId);
        }
        cartRepository.delete(cart);
    }
}

