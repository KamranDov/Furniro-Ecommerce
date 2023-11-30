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

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final ProductRepository repository;
    private final WishListRepository wishListRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public void add(ProductRequest productRequest) {
        Product product=new Product();
        product.setName(productRequest.getName());
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setLongDescription(productRequest.getLongDescription());
        product.setName(productRequest.getName());
        product.setName(productRequest.getName());
        product.setName(productRequest.getName());
        wishListRepository.save(product);
    }

    @Override
    public void delete(Long productId){
        if(productId==null){
            throw new NullPointerException("ProductId  cannot be null");
        }
        Product product = wishListRepository.findById(productId).orElseThrow();
        wishListRepository.delete(product);
    }

    @Override
    public List<WishListDTO> getWishListByUserId(Long userId) {
        if(userId==null){
            throw new NullPointerException("UserId cannot be null");
        }
//        UserResponse user =userService.getUser(userId);
//        List<WishList> wishListList=wishListRepository.findAllByUser(user);
        return null;
    }





}
