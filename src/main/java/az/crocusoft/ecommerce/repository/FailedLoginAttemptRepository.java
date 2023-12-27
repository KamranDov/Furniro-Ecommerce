package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.FailedLoginAttempt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FailedLoginAttemptRepository extends JpaRepository<FailedLoginAttempt,Long> {
    Optional<FailedLoginAttempt> findByUsername(String username);
    @Transactional
    Optional<FailedLoginAttempt> deleteByUsername(String username);

}
