package com.alibou.whatsappclone.message;

import com.alibou.whatsappclone.file.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {

    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .type(message.getType())
                .state(message.getState())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .createdDate(message.getCreatedDate())
                .media(FileUtils.readFileFromLocation(message.getMediaFilePath()))
                .build();
    }

}
