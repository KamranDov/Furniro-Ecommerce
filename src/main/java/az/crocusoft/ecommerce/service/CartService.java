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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, User user) {
        ProductVariation productVariation = productService.findById(addToCartDto.getProductId());

        double itemPrice = productVariation.getPrice();
        double discount = (itemPrice * productVariation.getDiscount()) / 100;
        double totalPrice = (itemPrice - discount) * addToCartDto.getQuantity();

        Cart cart = new Cart();
        cart.setProductVariation(productVariation);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(LocalDate.now());
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = cartList
                .stream()
                .map(CartItemDto::new)
                .toList();

        double totalPrice = cartItems
                .stream()
                .mapToDouble(cartItemDto -> {
                    double itemPrice = cartItemDto.getQuantity() * cartItemDto.getProductVariation().getPrice();
                    double discount = (itemPrice * cartItemDto.getProductVariation().getDiscount()) / 100;
                    return itemPrice - discount;
                })
                .sum();

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

