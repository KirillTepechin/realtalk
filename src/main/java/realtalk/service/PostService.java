package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtalk.model.Post;
import realtalk.model.User;
import realtalk.repository.PostRepository;
import realtalk.service.exception.PostNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly = true)
    public Post findPost(Long id) {
        final Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional
    public Post createPost(User user, String text) {
        Date date = new Date();
        final Post post = new Post(text, date, user);
        //validate
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, String text){
        final Post post = findPost(id);
        post.setText(text);
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> getUserPosts(User user){
        return postRepository.findAllByUser(user);
    }

    public List<Post> getFeed(User user) {
        return postRepository.findAllByUserIn(user.getSubscriptions());
    }

    @Transactional
    public void likePost(User user, Post post){
        if(!post.getLikes().contains(user)) {
            post.getLikes().add(user);
            postRepository.save(post);
        }else {
            post.getLikes().remove(user);
            postRepository.save(post);
        }
    }

    @Transactional
    public Post deletePost(Long id) {
        final Post post = findPost(id);
        postRepository.delete(post);
        return post;
    }

    @Transactional
    public void deleteAllUserPosts(User user){
        postRepository.deleteAll(postRepository.findAllByUser(user));
    }

}
