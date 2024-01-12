package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.product.id = :productId and r.approved = true")
    Page<Review> findAllByApprovedTrue(Long productId, Pageable pageable);
}
