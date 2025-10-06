package com.example.chatbotapi.repository;

import com.example.chatbotapi.model.Conversation;
import com.example.chatbotapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findByConversationOrderBySentAtAsc(Conversation conversation);
}