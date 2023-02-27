package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.Post;
import realtalk.model.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}