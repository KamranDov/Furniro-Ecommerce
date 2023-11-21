package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
