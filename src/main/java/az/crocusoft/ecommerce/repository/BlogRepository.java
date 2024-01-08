package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.Blog;
import az.crocusoft.ecommerce.model.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {


    List<Blog> findTop6ByOrderByDateDesc();

    List<Blog> findByCategoryCid(Integer categoryId);

    List<Blog> findByDateGreaterThanEqual(Date startDate);

    @Query(value = "SELECT COUNT(b.title) FROM Blog b WHERE b.category.id = :categoryId")
    Integer countByCategoryId(@Param("categoryId") Integer categoryId);

    Page<Blog> findByTitleContainingIgnoreCase(String title, Pageable pageable);


    @Query(value = "SELECT c.id as categoryId, c.name as categoryName, COUNT(b.pid) as blogCount FROM Blog b JOIN BlogCategory c ON b.category.id = c.id GROUP BY c.id, c.name")
    List<Map<String, Integer>> countBlogsPerCategory();


    @Query("SELECT b FROM Blog b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) AND b.category = :category")
    Page<Blog> findByTitleContainingIgnoreCaseAndCategory(@Param("title") String title, @Param("category") BlogCategory category, Pageable pageable);

    List<Blog> findBlogPostByCategory(BlogCategory category);

}
