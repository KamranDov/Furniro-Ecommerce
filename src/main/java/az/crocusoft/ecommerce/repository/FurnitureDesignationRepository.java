package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.FurnitureDesignation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurnitureDesignationRepository extends JpaRepository<FurnitureDesignation, Long> {

    boolean existsByName(String furnitureDesignationName);


}
