package com.example.chatbotapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotRequestDTO {
    
    private String message;
    private Long conversationId;
    private CustomerDTO customer;
}