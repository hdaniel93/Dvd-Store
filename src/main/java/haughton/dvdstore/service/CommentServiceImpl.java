package haughton.dvdstore.service;

import haughton.dvdstore.dao.CommentDao;
import haughton.dvdstore.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by danie on 04/04/2017.
 */
@Service
public class CommentServiceImpl implements CommentService {
@Autowired
private CommentDao commentDao;
    @Override
    public List<Comment> allCommentsByProductId(Long id) {
        return commentDao.allCommentsByProductId(id);
    }
    public void addComment(Long userId,Long productId,String text){
    commentDao.addComment(userId,productId,text);
    }


}
