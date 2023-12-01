package az.crocusoft.ecommerce.repository;


import az.crocusoft.ecommerce.model.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageUpload,String> {
}
