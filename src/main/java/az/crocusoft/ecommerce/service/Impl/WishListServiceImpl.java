package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.UserResponse;
import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import az.crocusoft.ecommerce.repository.ProductRepository;
import az.crocusoft.ecommerce.repository.WishListRepository;
import az.crocusoft.ecommerce.service.AuthenticationService;
import az.crocusoft.ecommerce.service.ProductService;
import az.crocusoft.ecommerce.service.UserService;
import az.crocusoft.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.WatchService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final ProductRepository repository;
    private final WishListRepository wishListRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public void add(WishListDTO wishListDTO) {
        Long productId=wishListDTO.getId();
        Long userId=wishListDTO.getUserId();
        Product product=productService.findProductById(productId);
        if(product==null){
            System.out.println("nulllll");
        }
        User user=userService.findUserById(userId);
        if (user==null){
            System.out.println("nulll");
        }
        WishList wishList=WishList.builder()
                .product(product)
                .user(user).build();
        wishListRepository.save(wishList);
    }

    @Override
    public void delete(Long productId){
        if(productId==null){
            throw new NullPointerException("ProductId  cannot be null");
        }
        Product product=productService.findProductById(productId);
        WishList wishList=wishListRepository.findByProduct(product);
        wishListRepository.delete(wishList);
    }

    @Override
    public List<WishListDTO> getWishListByUserId(Long userId) {
        if(userId==null){
            throw new NullPointerException("UserId cannot be null");
        }
        User user =userService.findUserById(userId);
        List<WishList> wishListList=wishListRepository.findAllByUser(user);

        List<WishListDTO> wishListDTOList = wishListList.stream()
                .map(this::mapper)
                .collect(Collectors.toList());
        return wishListDTOList;
    }
    public WishListDTO mapper(WishList wishList){
        WishListDTO wishListDTO=WishListDTO.builder()
                .id(wishList.getProduct().getId())
                .userId(wishList.getUser().getId()).build();
        return wishListDTO;
    }





}
