package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
