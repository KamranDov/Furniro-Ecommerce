package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut,Integer> {




}
