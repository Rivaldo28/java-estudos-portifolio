package com.ribot.chatbot.repository;

import com.ribot.chatbot.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Documents, Long> {
}
