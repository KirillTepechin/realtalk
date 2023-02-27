package realtalk.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NonNull
    public String name;
    public String image;

    @OneToMany
    @JoinColumn(name = "chat_id")
    public List<Message> messages;
    @NonNull
    @ManyToMany(mappedBy = "chats")
    public List<User> users;
}
