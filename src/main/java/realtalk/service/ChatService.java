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
import realtalk.util.FileUploadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
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
    //TODO:Не связывается
    public Long createChat(String name, List<Long> usersId) {
        List<User> users = new ArrayList<>();
        if(usersId != null) {
            users = usersId.stream().map(id -> userService.findUser(id)).toList();
        }
        final Chat chat = new Chat(name, users);
        //validate
        return chatRepository.save(chat).getId();
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
        return chatRepository.save(curChat);
    }

    @Transactional
    public Chat addUsersToChat(List<Long> userIds, Long id) {
        final Chat curChat = findChat(id);
        userIds.forEach(user -> curChat.getUsers().add(userService.findUser(id)));

        return chatRepository.save(curChat);
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
