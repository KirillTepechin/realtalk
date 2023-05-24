package realtalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import realtalk.dto.MessageDto;
import realtalk.dto.MessageOnCreateDto;
import realtalk.dto.MessageOnUpdateDto;
import realtalk.model.User;
import realtalk.service.MessageService;

@CrossOrigin(origins = "*")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("create-message/{id}")
    public void sendMessage(MessageOnCreateDto messageDto, @DestinationVariable Long id) {

        messageService.createMessage(messageDto.getUser().getLogin(), id, messageDto.getText());
    }

    @MessageMapping("update-message/{id}")
    public void updateMessage(@DestinationVariable Long id, MessageOnUpdateDto messageDto) {
        messageService.updateMessage(id, messageDto.getText());
    }

    @MessageMapping("delete-message/{id}")
    public void deleteMessage(@DestinationVariable Long id) {
        messageService.deleteMessage(id);
    }
}
