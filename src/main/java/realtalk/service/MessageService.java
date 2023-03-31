package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtalk.dto.MessageDto;
import realtalk.dto.MessageOnCreateDto;
import realtalk.mapper.MessageMapper;
import realtalk.model.*;
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
    private SimpMessagingTemplate simpMessagingTemplate;

    @Transactional(readOnly = true)
    public Message findMessage(Long id) {
        final Optional<Message> message = messageRepository.findById(id);
        //return message.orElseThrow(() -> new ChatNotFoundException(id));
        return message.orElse(null);
    }

    @Transactional
    public void createMessage(User user, Long chatId, String text) {
        Chat chat = chatService.findChat(chatId);
        Date date = new Date();

        final Message message = new Message(text, date, chat, user);
        messageRepository.save(message);

        simpMessagingTemplate.convertAndSend("/chat/"+chatId, messageMapper.toMessageOnCreateDto(message));
    }

    @Transactional
    public void updateMessage(Long id, String text){
        final Message message = findMessage(id);
        message.setText(text);
        messageRepository.save(message);

        simpMessagingTemplate.convertAndSend("/chat/"+message.getChat().getId(), messageMapper.toMessageOnUpdateDto(message));
    }

    @Transactional
    public void deleteMessage(Long id){
        final Message message = findMessage(id);
        messageRepository.delete(message);

        simpMessagingTemplate.convertAndSend("/chat/"+message.getChat().getId(), messageMapper.toMessageOnDeleteDto(message));
    }
}
