package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtalk.model.Chat;
import realtalk.model.User;
import realtalk.repository.ChatRepository;
import realtalk.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Chat> findAllChats() {
        return chatRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Chat findChat(Long id) {
        final Optional<Chat> chat = chatRepository.findById(id);
        //return chat.orElseThrow(() -> new ChatNotFoundException(id));
        return chat.orElse(null);
    }

    @Transactional
    public Chat addChat(String name, List<Long> usersId) {
        List<User> users = new ArrayList<>();
        if(usersId != null) {
            //TODO: if ID doesn't exist??
            users = usersId.stream().map(id -> userRepository.findById(id).get()).toList();
        }
        final Chat chat = new Chat(name, users);
        //validate
        return chatRepository.save(chat);
    }

    //TODO: update chat
//    @Transactional
//    public Chat updateChat(){
//
//    }

    @Transactional
    public Chat deleteChat(Long id) {
        final Chat curChat = findChat(id);
        chatRepository.delete(curChat);
        return curChat;
    }

    @Transactional
    public void deleteAllChats() {
        chatRepository.deleteAll();
    }
}
