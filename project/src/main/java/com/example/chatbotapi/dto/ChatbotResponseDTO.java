package com.example.chatbotapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotResponseDTO {
    
    private String message;
    private Long conversationId;
    private boolean requiresCustomerInfo;
    private String capturedData;
}