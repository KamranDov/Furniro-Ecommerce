package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

        Category findByName(String categoryName);
        Page<Category> findAllByOrderByNameAsc(Pageable pageable);
        boolean existsByName(String categoryName);
}
