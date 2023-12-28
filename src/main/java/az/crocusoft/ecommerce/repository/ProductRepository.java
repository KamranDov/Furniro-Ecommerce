package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    String FIND_ALL_PUBLISHED_PRODUCTS_QUERY =
            "SELECT * FROM (SELECT *, " +
                    "(SELECT MIN(price) FROM product_variations WHERE product_id = p.product_id) as price " +
                    "FROM products p " +
                    "WHERE p.published=true " +
                    "AND (LOWER(p.name) LIKE %:keyword% OR LOWER(p.title) LIKE %:keyword%) " +
                    "AND (:designationId IS NULL OR p.product_id IN " +
                    "(SELECT pd.product_id FROM product_designations pd WHERE pd.designation_id = :designationId)) " +
                    "AND (:categoryId IS NULL OR p.category_id = :categoryId)) as products_with_price";

    String COUNT_PRODUCTS_QUERY =
            "SELECT count(*) FROM products p " +
                    "WHERE p.published=true " +
                    "AND (LOWER(p.name) LIKE %:keyword% OR LOWER(p.title) LIKE %:keyword%) " +
                    "AND (:designationId IS NULL OR p.product_id IN " +
                    "(SELECT pd.product_id FROM product_designations pd WHERE pd.designation_id = :designationId)) " +
                    "AND (:categoryId IS NULL OR p.category_id = :categoryId)";


    @Query(value = FIND_ALL_PUBLISHED_PRODUCTS_QUERY,
            countQuery = COUNT_PRODUCTS_QUERY,
            nativeQuery = true)
    Page<Product> findProductsWithMinPriceAndKeywordAndDesignationAndCategory(
            @Param("keyword") String keyword,
            @Param("designationId") Long designationId,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.variations")
    List<Product> findAllWithVariations();

}
