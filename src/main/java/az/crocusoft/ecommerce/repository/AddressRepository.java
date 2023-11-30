package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.dto.AddressDto;
import az.crocusoft.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    List<AddressDto> findByUserId(Long id);




}
