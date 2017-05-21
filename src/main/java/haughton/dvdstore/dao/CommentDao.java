package haughton.dvdstore.dao;

import haughton.dvdstore.model.Comment;
import haughton.dvdstore.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by danie on 04/04/2017.
 */
@Repository
public interface CommentDao extends CrudRepository<Comment,Long> {
    @Query("select c from Comment c where c.product.id = :id order by c.id desc ")
    List<Comment> allCommentsByProductId(@Param("id") Long id);
    @Transactional @Modifying @Query (value="insert into comment (product_id,user_id,text) values (:productId,:userId,:text)",nativeQuery = true)
    int addComment(@Param("userId") Long userId,@Param("productId")Long productId,@Param("text")String text);
}
