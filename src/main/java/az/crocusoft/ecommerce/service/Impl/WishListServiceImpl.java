package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.exception.UserNotFoundException;
import az.crocusoft.ecommerce.exception.WishListNotFoundException;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import az.crocusoft.ecommerce.repository.WishListRepository;
import az.crocusoft.ecommerce.service.ProductService;
import az.crocusoft.ecommerce.service.UserService;
import az.crocusoft.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public void add(WishListDTO wishListDTO) {
        Long productId=wishListDTO.getId();
        Long userId=wishListDTO.getUserId();
        Product product=productService.findProductById(productId);
        User user=userService.findUserById(userId);
        WishList wishList=WishList.builder()
                .product(product)
                .user(user).build();
        wishListRepository.save(wishList);
        log.info("wishlist added in database");
    }

    @Override
    public void delete(WishListDTO wishListDTO){
        Long productId=wishListDTO.getId();
        Long userId=wishListDTO.getUserId();

        Product product=productService.findProductById(productId);
        User user=userService.findUserById(userId);
        WishList wishList=wishListRepository.findByProductAndUser(product,user);
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
                .map(this::mapper)
                .collect(Collectors.toList());
    }
    public WishListDTO mapper(WishList wishList){
        return WishListDTO.builder()
                .id(wishList.getProduct().getId())
                .userId(wishList.getUser().getId()).build();
    }

}
