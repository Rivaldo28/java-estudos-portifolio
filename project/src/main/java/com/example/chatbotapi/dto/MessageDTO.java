package com.example.chatbotapi.dto;

import com.example.chatbotapi.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    
    private Long id;
    private String content;
    private Message.SenderType senderType;
    private LocalDateTime sentAt;
    private Long conversationId;
}