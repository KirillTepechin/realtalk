package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import realtalk.dto.ChatDto;
import realtalk.model.Chat;
import realtalk.model.User;
import realtalk.repository.ChatRepository;
import realtalk.repository.UserRepository;
import realtalk.service.exception.ChatNotFoundException;
import realtalk.service.exception.UserNotFoundException;
import realtalk.util.FileUploadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Transactional(readOnly = true)
    public List<Chat> findAllChatsByUser(User user) {
        return chatRepository.findAllByUsersLoginOrderByLastMessageDate(user.getLogin());
    }

    @Transactional(readOnly = true)
    public Chat findChat(Long id) {
        final Optional<Chat> chat = chatRepository.findById(id);
        return chat.orElseThrow(() -> new ChatNotFoundException(id));
    }

    @Transactional
    public Chat createChat(String name, boolean isPrivate, List<Long> usersId) {
        List<User> users = usersId.stream().map(id -> userService.findUser(id)).toList();
        final Chat chat = new Chat(name, isPrivate, users);
        chatRepository.save(chat);
        users.forEach(user -> {
            user.getChats().add(chat);
            userRepository.save(user);
        });
        //validate
        return chat;
    }

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

    @Transactional
    public Chat leaveChat(User user, Long id) {
        final Chat curChat = findChat(id);
        curChat.getUsers().remove(user);
        chatRepository.save(curChat);
        //TODO: wtf?????????????????
        User userDB = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
        userDB.getChats().remove(curChat);
        userRepository.save(userDB);
//        user.getChats().remove(curChat);
//        userRepository.save(user);
        return curChat;
    }

    @Transactional
    public Chat addUsersToChat(List<Long> userIds, Long id) {
        final Chat curChat = findChat(id);
        userIds.forEach(userId -> curChat.getUsers().add(userService.findUser(userId)));
        chatRepository.save(curChat);
        userIds.forEach(userId -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            user.getChats().add(curChat);
            userRepository.save(user);
        });
        return curChat;
    }

    @Transactional
    public Chat editChat(Long id, String name, MultipartFile image) {
        final Chat curChat = findChat(id);
        if(name != null && !name.isBlank())
            curChat.setName(name);
        if(image!=null)
            curChat.setImage(fileUploadUtil.uploadFile(image));
        return chatRepository.save(curChat);
    }
}
