package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.Chat;
import realtalk.model.User;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUsersLoginOrderByLastMessageDate(String login);
}