package realtalk.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(length = 1000)
    private String text;
    @NonNull
    private Date date;
    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    private String file;
    private Boolean isFileImage;
    @ManyToOne
    @JoinColumn(name = "reply_post_id")
    private Post replyPost;

    @ManyToMany
    @JoinTable(
            name = "message_read_by",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> readBy;
}
