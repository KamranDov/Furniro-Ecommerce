package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.wishlist.WishList;

import java.util.List;

public interface WishListService {


    WishListDTO add(Product product);

    void delete(Long wishListId);

    List<WishList> getWishListByUserId(Long userId);
}
