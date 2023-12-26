package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.dto.request.ProductRequest;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WishListService {


    void add(Long productVariationId);

    void delete(Long productId);

    List<WishListDTO> getWishListByUserId(Long userId);
}
