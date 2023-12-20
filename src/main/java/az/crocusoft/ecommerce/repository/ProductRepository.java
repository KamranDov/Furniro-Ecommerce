package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByPublishedIsTrue(Pageable pageable);

    @Query(value = "SELECT * FROM (SELECT *, " +
            "(SELECT MIN(price) FROM product_variations WHERE product_id = p.product_id) as price " +
            "FROM products p WHERE p.published=true " +
            "AND (LOWER(p.name) LIKE %:keyword% OR LOWER(p.title) LIKE %:keyword%)) as products_with_price",
            countQuery = "SELECT count(*) FROM products p WHERE p.published=true " +
                    "AND (LOWER(p.name) LIKE %:keyword% OR LOWER(p.title) LIKE %:keyword%)",
            nativeQuery = true)
    Page<Product> findProductsWithMinPriceAndKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );


    Page<Product> findProductsByFurnitureDesignations_IdAndPublishedIsTrue(Long designationId,
                                                                           Pageable pageable);

    Page<Product> findByNameContaining(String keyword, Pageable pageDetails);


}
