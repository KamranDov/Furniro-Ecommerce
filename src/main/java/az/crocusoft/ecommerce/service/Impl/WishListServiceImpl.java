package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.exception.StockQuantityControlException;
import az.crocusoft.ecommerce.exception.UserAlreadyAddedThisProductWishList;
import az.crocusoft.ecommerce.exception.UserNotFoundException;
import az.crocusoft.ecommerce.exception.WishListNotFoundException;
import az.crocusoft.ecommerce.model.Cart;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import az.crocusoft.ecommerce.repository.WishListRepository;
import az.crocusoft.ecommerce.service.AuthenticationService;
import az.crocusoft.ecommerce.service.ProductService;
import az.crocusoft.ecommerce.service.UserService;
import az.crocusoft.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final AuthenticationService authenticationService;
    private final ProductService productService;
    private final UserService userService;
    private final FileService fileService;

    @Override
    public void add(Long productVariationId) {
        User user = authenticationService.getSignedInUser();
        ProductVariation productVariation = productService.findById(productVariationId);

        if (wishListRepository.existsByUserAndProductVariation(user, productVariation)) {
            log.warn("User already added this product to the wishlist");
            throw new UserAlreadyAddedThisProductWishList("User already added this product to the wishlist");
        }
                WishList wishList=WishList.builder()
                .productVariation(productVariation)
                .user(user).build();
        wishListRepository.save(wishList);
        log.info("wishlist added in database");
    }

    @Override
    public void delete(Long productId){
        User user = authenticationService.getSignedInUser();

        ProductVariation productVariation = productService.findById(productId);

        WishList wishList=wishListRepository.findByProductVariationAndUser(productVariation,user);
        if (wishList==null){
            throw new WishListNotFoundException("WishList is null");
        }
        wishListRepository.delete(wishList);
        log.info(productId+"No product deleted");
    }

    @Override
    public List<WishListDTO> getWishListByUserId(Long userId) {
        if(userId==null){
            throw new UserNotFoundException("User cannot be null");
        }
        User user =userService.findUserById(userId);
        List<WishList> wishListList=wishListRepository.findAllByUser(user);

        return wishListList.stream()
                .map(this::toWishListDTO)
                .collect(Collectors.toList());
    }

    private WishListDTO toWishListDTO(WishList wishList) {
        ProductVariation variation = wishList.getProductVariation();
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setVariationId(variation.getProductVariationiId());
        wishListDTO.setSku(variation.getSku());
        wishListDTO.setPrice(variation.getPrice());
        wishListDTO.setDiscount(variation.getDiscount());
        wishListDTO.setColor(variation.getColor());
        wishListDTO.setSize(variation.getSize());
        wishListDTO.setProductName(variation.getProduct().getName());
        wishListDTO.setProductId(variation.getProduct().getId());

        variation.getImages().forEach(
                image -> wishListDTO
                        .getImageUrls()
                        .add(fileService.getFullImagePath(image.getImageUrl()))
        );

        return wishListDTO;
    }
//    public WishListDTO mapper(WishList wishList){
//        return WishListDTO.builder()
//                .productVariation(wishList.getProductVariation())
//                .productName(wishList
//                        .getProductVariation()
//                        .getProduct()
//                        .getName())
//
//
//                .build();}

}
