package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.Post;
import realtalk.model.User;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
    List<Post> findAllByTagIn(Set<String> tags);
    List<Post> findAllByUserIn(Set<User> users);
}