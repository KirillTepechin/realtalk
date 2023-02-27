package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtalk.model.*;
import realtalk.repository.MessageRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Transactional(readOnly = true)
    public Message findMessage(Long id) {
        final Optional<Message> message = messageRepository.findById(id);
        //return message.orElseThrow(() -> new ChatNotFoundException(id));
        return message.orElse(null);
    }

    @Transactional
    public Message addMessage(User user, Chat chat, String text, Date date) {
        //TODO: привязывается ли к чату
        //TODO: отправка брокера
        final Message message = new Message(text, date, chat, user);
        //validate
        return messageRepository.save(message);
    }

    @Transactional
    public Message updateMessage(Long id, String text){
        final Message message = findMessage(id);
        message.setText(text);
        return messageRepository.save(message);
    }

    @Transactional
    public Message deleteMessage(Long id){
        final Message message = findMessage(id);
        messageRepository.delete(message);
        return message;
    }
}
