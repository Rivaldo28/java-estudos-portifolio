package com.example.chatbotapi.controller;

import com.example.chatbotapi.dto.ConversationDTO;
import com.example.chatbotapi.dto.MessageDTO;
import com.example.chatbotapi.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {
    
    private final ConversationService conversationService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ConversationDTO> getConversationById(@PathVariable Long id) {
        ConversationDTO conversation = conversationService.convertToDTO(
                conversationService.getConversationById(id)
        );
        return ResponseEntity.ok(conversation);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ConversationDTO>> getConversationsByCustomer(@PathVariable Long customerId) {
        List<ConversationDTO> conversations = conversationService.getConversationsByCustomer(customerId);
        return ResponseEntity.ok(conversations);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<List<ConversationDTO>> getConversationsByEmail(@PathVariable String email) {
        List<ConversationDTO> conversations = conversationService.getConversationsByCustomerEmail(email);
        return ResponseEntity.ok(conversations);
    }
    
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<ConversationDTO> createConversation(
            @PathVariable Long customerId,
            @RequestParam(required = false) String title) {
        ConversationDTO conversation = conversationService.createConversation(customerId, title);
        return new ResponseEntity<>(conversation, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConversationDTO> updateConversation(
            @PathVariable Long id,
            @RequestParam String title) {
        ConversationDTO updatedConversation = conversationService.updateConversation(id, title);
        return ResponseEntity.ok(updatedConversation);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Long id) {
        conversationService.deleteConversation(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<MessageDTO> addMessageToConversation(
            @PathVariable Long conversationId,
            @Valid @RequestBody MessageDTO messageDTO) {
        MessageDTO savedMessage = conversationService.addMessageToConversation(conversationId, messageDTO);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }
}