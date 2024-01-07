package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.dto.cart.AddToCartDto;
import az.crocusoft.ecommerce.dto.cart.CartDto;
import az.crocusoft.ecommerce.dto.cart.CartItemDto;
import az.crocusoft.ecommerce.exception.CartItemNotFoundException;
import az.crocusoft.ecommerce.exception.CartItemOwnershipException;
import az.crocusoft.ecommerce.exception.StockQuantityControlException;
import az.crocusoft.ecommerce.model.Cart;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import az.crocusoft.ecommerce.repository.CartRepository;
import az.crocusoft.ecommerce.repository.ProductVariationRepository;
import az.crocusoft.ecommerce.service.Impl.FileService;
import az.crocusoft.ecommerce.service.Impl.ProductServiceImpl;
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
    private final ProductServiceImpl productServiceImpl;
    private final ProductVariationRepository productVariationRepository;
    private final FileService fileService;

    public void addToCart(AddToCartDto addToCartDto, User user) {
        ProductVariation productVariation = productService.findById(addToCartDto.getProductVariationId());

        if(productVariation.getStockQuantity() < addToCartDto.getQuantity()){
            throw new StockQuantityControlException("We don't have as many products as you want in stock");
        }

        double itemPrice = productVariation.getPrice();
        double discount = (itemPrice * productVariation.getDiscount()) / 100;
        double discountedPrice = itemPrice - discount;

        Cart existingCart = cartRepository.findByProductVariationAndUser(productVariation, user);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + addToCartDto.getQuantity());
            cartRepository.save(existingCart);
        } else {
            Cart cart = new Cart();
            cart.setProductVariation(productVariation);
            cart.setUser(user);
            cart.setQuantity(addToCartDto.getQuantity());
            cart.setCreatedDate(LocalDate.now());
            cart.setDiscountedPrice(discountedPrice);

            cartRepository.save(cart);
        }
    }


    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = cartList
                .stream()
                .map(this::toCartItemDto
                )
                .toList();

        double totalPrice = cartItems
                .stream()
                .mapToDouble(cartItemDto -> {
                    double itemPrice = cartItemDto.getQuantity() * cartItemDto.getPrice();
                    double discount = (itemPrice * cartItemDto.getDiscount()) / 100;
                    return itemPrice - discount;
                })
                .sum();
        double totalPriceWithoutDiscount = cartItems
                .stream()
                .mapToDouble(cartItemDto -> {
                    return cartItemDto.getQuantity() * cartItemDto.getPrice();
                })
                .sum();

        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(totalPrice);
        cartDto.setCartItems(cartItems);
        cartDto.setTotalPriceWithoutDiscount(totalPriceWithoutDiscount);
        return cartDto;
    }

    private CartItemDto toCartItemDto(Cart cart) {
        ProductVariation variation = cart.getProductVariation();
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cart.getId());
        cartItemDto.setVariationId(variation.getProductVariationiId());
        cartItemDto.setQuantity(cart.getQuantity());
        cartItemDto.setSku(variation.getSku());
        cartItemDto.setPrice(variation.getPrice());
        cartItemDto.setDiscount(variation.getDiscount());
        cartItemDto.setColor(variation.getColor());
        cartItemDto.setSize(variation.getSize());
        cartItemDto.setStockQuantity(variation.getStockQuantity());
        cartItemDto.setProductName(variation.getProduct().getName());
        cartItemDto.setProductId(variation.getProduct().getId());
        Double subtotal=(variation.getPrice()*cart.getQuantity())-
                ((variation.getPrice()*cart.getQuantity())*
                variation.getDiscount())/100;
        cartItemDto.setSubtotal(subtotal);

        variation.getImages().forEach(
                image -> cartItemDto
                        .getImageUrls()
                        .add(fileService.getFullImagePath(image.getImageUrl()))
        );

        return cartItemDto;
    }
    private Double getTotalPrice(Cart cart){
        return productServiceImpl.getProductVariationSpecialPrice(cart.getProductVariation()) * cart.getQuantity();
    }


    public void clearAllCartsForUser(User user) {
        List<Cart> userCarts = cartRepository.findAllByUser(user);
        for (Cart cart : userCarts) {
            cartRepository.delete(cart);
        }}


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

