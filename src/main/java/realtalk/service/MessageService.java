package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtalk.bot.SimpleBot;
import realtalk.dto.MessageDto;
import realtalk.dto.MessageOnCreateDto;
import realtalk.mapper.MessageMapper;
import realtalk.model.*;
import realtalk.repository.ChatRepository;
import realtalk.repository.MessageRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private SimpleBot bot;

    @Transactional(readOnly = true)
    public Message findMessage(Long id) {
        final Optional<Message> message = messageRepository.findById(id);
        //return message.orElseThrow(() -> new ChatNotFoundException(id));
        return message.orElse(null);
    }

    @Transactional
    public void createMessage(String userLogin, Long chatId, String text) {
        Chat chat = chatService.findChat(chatId);
        User user = userService.findUserByLogin(userLogin);
        Date date = new Date();

        final Message message = new Message(text, date, chat, user);
        messageRepository.save(message);
        chat.setLastMessageDate(message.getDate());

        chatRepository.save(chat);

        simpMessagingTemplate.convertAndSend("/topic/"+chatId, messageMapper.toMessageOnCreateDto(message));

        User userBot = userService.findUserByLogin("bot");
        if(chat.getUsers().contains(userBot)){
            String textFromBot = bot.sayInReturn(text, true);
            Message messageFromBot = new Message(textFromBot, new Date(), chat, userBot);
            messageRepository.save(messageFromBot);
            chat.setLastMessageDate(messageFromBot.getDate());
            simpMessagingTemplate.convertAndSend("/topic/"+chatId, messageMapper.toMessageOnCreateDto(messageFromBot));
        }
    }

    @Transactional
    public void updateMessage(Long id, String text){
        final Message message = findMessage(id);
        message.setText(text);
        messageRepository.save(message);

        simpMessagingTemplate.convertAndSend("/topic/"+message.getChat().getId(), messageMapper.toMessageOnUpdateDto(message));
    }

    @Transactional
    public void deleteMessage(Long id){
        final Message message = findMessage(id);
        messageRepository.delete(message);

        simpMessagingTemplate.convertAndSend("/topic/"+message.getChat().getId(), messageMapper.toMessageOnDeleteDto(message));
    }
}
