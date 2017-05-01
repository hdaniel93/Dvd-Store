package haughton.dvdstore.service;

import haughton.dvdstore.model.Comment;
import haughton.dvdstore.model.Product;

import java.util.List;

/**
 * Created by danie on 04/04/2017.
 */
public interface CommentService {
    List<Comment> allCommentsByProductId(Long id);
    public void addComment(Long userId,Long productId,String text);
}
