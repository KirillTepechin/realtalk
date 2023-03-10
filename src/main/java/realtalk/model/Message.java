package realtalk.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NonNull
    public String text;
    @NonNull
    public Date date;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "chat_id")
    public Chat chat;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

}
