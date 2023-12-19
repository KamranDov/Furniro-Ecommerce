package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByPublishedIsTrue(Pageable pageable);

    @Query(value = "SELECT * FROM (SELECT *, " +
            "(SELECT MIN(price) FROM product_variations WHERE product_id = p.product_id) as min_price " +
            "FROM products p WHERE p.published=true) as products_with_price " +
            "ORDER BY min_price ASC",
            countQuery = "SELECT count(*) FROM products",
            nativeQuery = true)
    Page<Product> findProductsWithMinPriceAscOrder(Pageable pageable);

    @Query(value = "SELECT * FROM (SELECT *, " +
            "(SELECT MIN(price) FROM product_variations WHERE product_id = p.product_id) as min_price " +
            "FROM products p WHERE p.published=true) as products_with_price " +
            "ORDER BY min_price DESC",
            countQuery = "SELECT count(*) FROM products",
            nativeQuery = true)
    Page<Product> findProductsWithMinPriceDescOrder(Pageable pageable);


    Page<Product> findProductsByFurnitureDesignations_IdAndPublishedIsTrue(Long designationId,
                                                                           Pageable pageable);

    Page<Product> findByNameContaining(String keyword, Pageable pageDetails);


}
