package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory,Integer> {

    Optional<BlogCategory> findCategoryByName(String categoryName);
}
