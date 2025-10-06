package com.ribot.chatbot.repository;

import com.ribot.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCodeUser(Double codeUser);
}
