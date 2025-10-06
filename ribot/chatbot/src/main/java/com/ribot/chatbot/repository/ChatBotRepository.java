package com.ribot.chatbot.repository;

import com.ribot.chatbot.model.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {

    boolean existsByInput(String input);

    @Query(value = "SELECT c.output FROM " +
            "ChatBot c " +
            "WHERE c.input LIKE %:inputValue%", nativeQuery = true)
    List<String> findOutputByInput(@Param("inputValue") String inputValue);

    @Query(value = "SELECT c.* FROM ChatBot c WHERE c.chat_id = :chatId", nativeQuery = true)
    List<ChatBot> findByChatId(@Param("chatId") Integer chatId);

}
