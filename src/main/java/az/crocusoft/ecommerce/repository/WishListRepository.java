package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.dto.WishListDTO;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.user.User;
import az.crocusoft.ecommerce.model.wishlist.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {
    WishList findByProduct(Product product);
    List<WishList> findAllByUser(User user);
    WishList findByUser(User user);
}
