package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
