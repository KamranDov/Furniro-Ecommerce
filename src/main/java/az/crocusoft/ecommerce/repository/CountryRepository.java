package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
}
