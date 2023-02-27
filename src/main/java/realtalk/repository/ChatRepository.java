package realtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realtalk.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}