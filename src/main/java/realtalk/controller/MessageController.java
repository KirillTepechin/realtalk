package realtalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realtalk.dto.MessageDto;
import realtalk.model.User;
import realtalk.service.MessageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("send-to-chat/{id}")
    public void sendMessage(MessageDto messageDto, @AuthenticationPrincipal User user, @PathVariable Long id) throws Exception {
        messageService.createMessage(user, id, messageDto.getText());
    }

    @MessageMapping("update-message/{id}")
    public void updateMessage(@PathVariable Long id, MessageDto messageDto) {
        messageService.updateMessage(id, messageDto.getText());
    }

    @MessageMapping("delete-message/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}