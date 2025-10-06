package com.example.chatbotapi.service;

import com.example.chatbotapi.dto.ChatbotRequestDTO;
import com.example.chatbotapi.dto.ChatbotResponseDTO;
import com.example.chatbotapi.dto.CustomerDTO;
import com.example.chatbotapi.dto.MessageDTO;
import com.example.chatbotapi.model.Conversation;
import com.example.chatbotapi.model.Customer;
import com.example.chatbotapi.model.Message;
import com.example.chatbotapi.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatbotService {
    
    private final NlpService nlpService;
    private final CustomerService customerService;
    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;
    
    @Transactional
    public ChatbotResponseDTO processMessage(ChatbotRequestDTO request) {
        String userMessage = request.getMessage();
        Map<String, String> extractedData = nlpService.extractCustomerData(userMessage);
        String intent = nlpService.categorize(userMessage);
        boolean containsCustomerData = nlpService.containsCustomerData(userMessage);
        
        // Handle customer data if present in message
        Customer customer = null;
        if (request.getCustomer() != null && request.getCustomer().getEmail() != null) {
            customer = customerService.findOrCreateCustomer(request.getCustomer());
        } else if (!extractedData.isEmpty()) {
            CustomerDTO extractedCustomer = new CustomerDTO();
            extractedCustomer.setEmail(extractedData.get("email"));
            extractedCustomer.setPhoneNumber(extractedData.get("phoneNumber"));
            customer = customerService.findOrCreateCustomer(extractedCustomer);
        }
        
        // Create or retrieve conversation
        Conversation conversation;
        if (request.getConversationId() != null) {
            conversation = conversationService.getConversationById(request.getConversationId());
        } else if (customer != null) {
            conversation = new Conversation();
            conversation.setCustomer(customer);
            conversation.setTitle("Conversation with " + customer.getEmail());
            conversation = conversationRepository.save(conversation);
        } else {
            // Create temporary conversation without customer
            conversation = new Conversation();
            conversation.setTitle("New conversation");
            conversation = conversationRepository.save(conversation);
        }
        
        // Save user message
        MessageDTO userMessageDTO = new MessageDTO();
        userMessageDTO.setContent(userMessage);
        userMessageDTO.setSenderType(Message.SenderType.USER);
        userMessageDTO.setConversationId(conversation.getId());
        conversationService.addMessageToConversation(conversation.getId(), userMessageDTO);
        
        // Generate bot response
        String botResponseText = nlpService.generateResponse(intent, extractedData);
        
        // Save bot message
        MessageDTO botMessageDTO = new MessageDTO();
        botMessageDTO.setContent(botResponseText);
        botMessageDTO.setSenderType(Message.SenderType.BOT);
        botMessageDTO.setConversationId(conversation.getId());
        conversationService.addMessageToConversation(conversation.getId(), botMessageDTO);
        
        // Create response DTO
        ChatbotResponseDTO response = new ChatbotResponseDTO();
        response.setMessage(botResponseText);
        response.setConversationId(conversation.getId());
        response.setRequiresCustomerInfo(customer == null && intent.equals("contact"));
        response.setCapturedData(extractedData.isEmpty() ? null : extractedData.toString());
        
        return response;
    }
}