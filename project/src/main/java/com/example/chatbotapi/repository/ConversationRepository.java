package com.example.chatbotapi.repository;

import com.example.chatbotapi.model.Conversation;
import com.example.chatbotapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
    List<Conversation> findByCustomerOrderByStartedAtDesc(Customer customer);
    
    List<Conversation> findByCustomerEmailOrderByStartedAtDesc(String email);
}