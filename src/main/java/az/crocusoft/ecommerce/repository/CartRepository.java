package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.Cart;
import az.crocusoft.ecommerce.model.User;
import org.mapstruct.control.MappingControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    Cart findByUser(User user);
    List<Cart> findAllByUser(User user);

    List<Cart> findCartByCreatedDateBefore(LocalDate localDate);
}


