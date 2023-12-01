package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.Blog;
import az.crocusoft.ecommerce.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {


    List<Blog> findByDateGreaterThanEqual(Date startDate);

    @Query(value = "SELECT COUNT(b.title) FROM Blog b WHERE b.category.id = :categoryId")
    Integer countByCategoryId(@Param("categoryId") Integer categoryId);


    List<Blog> findBlogPostByCategory(BlogCategory category);

}
