package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String tagName);
}
