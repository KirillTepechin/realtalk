package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtalk.model.Comment;
import realtalk.model.Post;
import realtalk.model.User;
import realtalk.repository.CommentRepository;
import realtalk.service.exception.CommentNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Comment findComment(Long id) {
        final Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional
    public Comment addComment(User user, Post post, String text, Date date) {
        final Comment comment = new Comment(user, post, text, date);
        //validate
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, String text){
        final Comment comment = findComment(id);
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Transactional
    public void likeComment(User user, Comment comment){
        if(!comment.getLikes().contains(user)) {
            comment.getLikes().add(user);
            commentRepository.save(comment);
        }else {
            comment.getLikes().remove(user);
            commentRepository.save(comment);
        }
    }

    @Transactional
    public Comment deleteComment(Long id) {
        final Comment comment = findComment(id);
        commentRepository.delete(comment);
        return comment;
    }

    @Transactional
    public void deleteAllComments() {
        commentRepository.deleteAll();
    }
}
