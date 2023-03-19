package realtalk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import realtalk.dto.ChatCreateDto;
import realtalk.dto.ChatDto;
import realtalk.mapper.ChatMapper;
import realtalk.model.User;
import realtalk.service.ChatService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/chat")
//TODO: проверить все методы
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMapper chatMapper;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    //TODO: будут ли у чатов создатели с доп правами, например на удаление?
    public Long createChat(@AuthenticationPrincipal User user, @RequestBody ChatCreateDto chatCreateDto){
        chatCreateDto.getUserIds().add(user.getId());
        //Возвращаем id чата, что бы на фронте(☠), после создания чата, сразу переходить на уникальный чат
        return chatService.createChat(chatCreateDto.getName(), chatCreateDto.getUserIds());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<ChatDto> getChatsByUser(@AuthenticationPrincipal User user){
        return chatService.findAllChatsByUser(user).stream()
                .map(chat -> chatMapper.toChatDto(chat))
                .toList();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ChatDto editChat(@PathVariable Long id, @RequestParam(required = false) String name,
                            @RequestParam(required = false) MultipartFile image ){
        return chatMapper.toChatDto(chatService.editChat(id, name, image));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("{id}/add-members")
    public ChatDto addUsersToChat(@PathVariable Long id, @RequestBody List<Long> userIds){
        return chatMapper.toChatDto(chatService.addUsersToChat(userIds, id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/leave/{id}")
    public ChatDto leaveChat(@AuthenticationPrincipal User user, Long id){
        return chatMapper.toChatDto(chatService.leaveChat(user, id));
    }

    @DeleteMapping("/{id}")
    public ChatDto deleteChat(@PathVariable Long id){
        return chatMapper.toChatDto(chatService.deleteChat(id));
    }
}
