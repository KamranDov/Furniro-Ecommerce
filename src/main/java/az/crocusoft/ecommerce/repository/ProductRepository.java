package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
