package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIsPublishedTrue();


}
