package realtalk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import realtalk.dto.MessageDto;
import realtalk.dto.MessageOnCreateDto;
import realtalk.dto.MessageOnDeleteDto;
import realtalk.dto.MessageOnUpdateDto;
import realtalk.model.Message;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    Message fromMessageDto(MessageDto messageDto);
    //MessageDto toMessageDto(Message message);
    MessageOnCreateDto toMessageOnCreateDto(Message message);
    MessageOnDeleteDto toMessageOnDeleteDto(Message message);
    MessageOnUpdateDto toMessageOnUpdateDto(Message message);

}
