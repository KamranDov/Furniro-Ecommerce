package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long>{

}
