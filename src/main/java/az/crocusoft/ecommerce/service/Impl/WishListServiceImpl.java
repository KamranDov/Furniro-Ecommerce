package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.user.User;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import az.crocusoft.ecommerce.repository.ProductRepository;
import az.crocusoft.ecommerce.repository.WishListRepository;
import az.crocusoft.ecommerce.service.ProductService;
import az.crocusoft.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.WatchService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final ProductRepository repository;
    private final WishListRepository wishListRepository;
    private final ProductService productService;

    @Override
    public WishListDTO add(Product product) {
        wishListRepository.save(product);
        return wishListDTO;
    }

    @Override
    public void delete(Long productId){
        Product product = repository.findById(productId).orElseThrow();
        WishList wishList=wishListRepository.findByProduct(product);
        wishListRepository.delete(wishList);
    }

    @Override
    public List<WishList> getWishListByUserId(Long userId) {
        User user =userService.getUserById(userId);
        List<WishList> wishListList=wishListRepository.findAllByUser(user);
        return wishListList;
    }

    public WishList mapper(WishListDTO wishListDTO){
        Product product = repository.findById(wishListDTO.getId()).orElseThrow();
        //Product product=productService.getProductById(wishListDTO.getId());
        User user =new User() ;
        WishList wishList=WishList.builder()
                .user(user)
                .product(product)
                .build();
        return wishList;
    }
    public WishListDTO mapper2(WishList wishList){
//        Product product = repository.findById(wishListDTO.getId()).orElseThrow();
//        //Product product=productService.getProductById(wishListDTO.getId());
//        User user =new User() ;
//        WishList wishList=WishList.builder()
//                .user(user)
//                .product(product)
//                .build();
//        return wishList;
    }

}
