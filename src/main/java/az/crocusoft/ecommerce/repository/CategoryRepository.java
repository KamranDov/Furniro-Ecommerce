package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findCategoryByName(String categoryName);
}
