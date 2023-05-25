package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import realtalk.model.Post;
import realtalk.model.User;
import realtalk.repository.PostRepository;
import realtalk.service.exception.PostNotFoundException;
import realtalk.util.FileUploadUtil;

import java.util.*;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Transactional(readOnly = true)
    public Post findPost(Long id) {
        final Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional
    public Post createPost(User user, String text, Set<String> tags) {
        Date date = new Date();
        final Post post = new Post(date, tags, user);
        if(text != null){
            post.setText(text);
        }
        //validate
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, String text, Set<String> tags){
        final Post post = findPost(id);
        if(text != null)
            post.setText(text);
        post.setTags(tags);
        return postRepository.save(post);
    }

    @Transactional
    public Post uploadPhotoForPost(Long id, MultipartFile image) {
        final Post post = findPost(id);
        if(image!=null)
            post.setPhoto(fileUploadUtil.uploadFile(image));
        else
            post.setPhoto(null);
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> getUserPosts(User user){
        return postRepository.findAllByUserOrderByDateDesc(user);
    }

    public List<Post> getFeed(User user) {
        return postRepository.findAllByUserInOrderByDateDesc(user.getSubscriptions());
    }

    public List<Post> getRecommendFeed(User user) {
        List<Post> recs = postRepository.findAllByTagsInOrderByDateDesc(user.getTags());
        recs.removeIf(post -> Objects.equals(post.getUser().getId(), user.getId()));
        return recs;
    }

    @Transactional
    public boolean likePost(User user, Post post){
        if(!post.getLikes().contains(user)) {
            post.getLikes().add(user);
            postRepository.save(post);

            return true;
        }else {
            post.getLikes().remove(user);
            postRepository.save(post);

            return false;
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
        postRepository.deleteAll(postRepository.findAllByUserOrderByDateDesc(user));
    }

}
