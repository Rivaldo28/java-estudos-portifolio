package com.example.chatbotapi.controller;

import com.example.chatbotapi.dto.ChatbotRequestDTO;
import com.example.chatbotapi.dto.ChatbotResponseDTO;
import com.example.chatbotapi.service.ChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {
    
    private final ChatbotService chatbotService;
    
    @PostMapping("/message")
    public ResponseEntity<ChatbotResponseDTO> sendMessage(@Valid @RequestBody ChatbotRequestDTO request) {
        ChatbotResponseDTO response = chatbotService.processMessage(request);
        return ResponseEntity.ok(response);
    }
}