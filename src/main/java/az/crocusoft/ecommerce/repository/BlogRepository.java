package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.dto.BlogCategoryCount;
import az.crocusoft.ecommerce.model.Blog;
import az.crocusoft.ecommerce.model.Category;
import az.crocusoft.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {


    List<Blog> findByDateGreaterThanEqual(Date startDate);
    List<Blog> findBlogPostByUser(User username);
    List<Blog> findBlogPostByUser(User username, Sort sort);
    List<Blog> findBlogPostByCategory(Category category);
    Page<Blog> findBlogPostByCategory(Category category, Pageable pageable);
}
