package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
