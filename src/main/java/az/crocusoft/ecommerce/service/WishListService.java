package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WishListService {


    void add(WishListDTO wishListDTO);

    void delete(WishListDTO wishListDTO);

    List<WishListDTO> getWishListByUserId(Long userId);
}
