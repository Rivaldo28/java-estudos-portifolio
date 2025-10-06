package com.example.chatbotapi.service;

import com.example.chatbotapi.dto.ConversationDTO;
import com.example.chatbotapi.dto.MessageDTO;
import com.example.chatbotapi.exception.ResourceNotFoundException;
import com.example.chatbotapi.model.Conversation;
import com.example.chatbotapi.model.Customer;
import com.example.chatbotapi.model.Message;
import com.example.chatbotapi.repository.ConversationRepository;
import com.example.chatbotapi.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationService {
    
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final CustomerService customerService;
    
    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found with id: " + id));
    }
    
    public List<ConversationDTO> getConversationsByCustomer(Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Conversation> conversations = conversationRepository.findByCustomerOrderByStartedAtDesc(customer);
        
        return conversations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ConversationDTO> getConversationsByCustomerEmail(String email) {
        List<Conversation> conversations = conversationRepository.findByCustomerEmailOrderByStartedAtDesc(email);
        
        return conversations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ConversationDTO createConversation(Long customerId, String title) {
        Customer customer = customerService.getCustomerById(customerId);
        
        Conversation conversation = new Conversation();
        conversation.setCustomer(customer);
        conversation.setTitle(title != null ? title : "New Conversation");
        
        Conversation savedConversation = conversationRepository.save(conversation);
        return convertToDTO(savedConversation);
    }
    
    public ConversationDTO updateConversation(Long id, String title) {
        Conversation conversation = getConversationById(id);
        conversation.setTitle(title);
        
        Conversation updatedConversation = conversationRepository.save(conversation);
        return convertToDTO(updatedConversation);
    }
    
    @Transactional
    public void deleteConversation(Long id) {
        if (!conversationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Conversation not found with id: " + id);
        }
        conversationRepository.deleteById(id);
    }
    
    public MessageDTO addMessageToConversation(Long conversationId, MessageDTO messageDTO) {
        Conversation conversation = getConversationById(conversationId);
        
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setSenderType(messageDTO.getSenderType());
        message.setConversation(conversation);
        
        Message savedMessage = messageRepository.save(message);
        conversation.getMessages().add(savedMessage);
        conversationRepository.save(conversation);
        
        return convertToMessageDTO(savedMessage);
    }
    
    private ConversationDTO convertToDTO(Conversation conversation) {
        List<Message> messages = messageRepository.findByConversationOrderBySentAtAsc(conversation);
        List<MessageDTO> messageDTOs = messages.stream()
                .map(this::convertToMessageDTO)
                .collect(Collectors.toList());
        
        ConversationDTO dto = new ConversationDTO();
        dto.setId(conversation.getId());
        dto.setTitle(conversation.getTitle());
        dto.setStartedAt(conversation.getStartedAt());
        dto.setUpdatedAt(conversation.getUpdatedAt());
        dto.setCustomerId(conversation.getCustomer().getId());
        dto.setCustomerEmail(conversation.getCustomer().getEmail());
        dto.setMessages(messageDTOs);
        
        return dto;
    }
    
    private MessageDTO convertToMessageDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSenderType(message.getSenderType());
        dto.setSentAt(message.getSentAt());
        dto.setConversationId(message.getConversation().getId());
        
        return dto;
    }
}