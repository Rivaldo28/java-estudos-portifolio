package com.ribot.chatbot.service;


import com.ribot.chatbot.model.User;
import com.ribot.chatbot.model.dto.UserRequestDTO;
import com.ribot.chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CodDataService codDataService;

//    private CodDataService codDataService = new CodDataService();

    public User findByCodeUser(Double codeUser) {
        return userRepository.findByCodeUser(codeUser);
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    public User insertUser(@RequestBody User user) {
        if (user.getCodeUser() == null || user.getCodeUser() <= 0) {
            user.setCodeUser((double) codDataService.cod());
        }
        if (user.getActivate() == null) {
            user.setActivate(true);
        }
        if (user.getFullName() == null) {
            user.setFullName("");
        }
        if (user.getUserName() == null) {
            user.setUserName("");
        }
        if (user.getEmail() == null) {
            user.setEmail("");
        }
        if (user.getPassword() == null) {
            user.setPassword("");
        }

        return userRepository.save(user);
    }

    public String updateUser(@RequestBody UserRequestDTO requestDTO) {
        try {
            User user = userRepository.findById(requestDTO.getId()).orElse(null);

            if (user != null) {
                if (requestDTO.getCodeUser() != null) {
                    user.setCodeUser(requestDTO.getCodeUser());
                }
                if (requestDTO.getActivate() != null) {
                    user.setActivate(requestDTO.getActivate());
                }
                if (requestDTO.getFullName() != null) {
                    user.setFullName(requestDTO.getFullName());
                }
                if (requestDTO.getUserName() != null) {
                    user.setUserName(requestDTO.getUserName());
                }
                if (requestDTO.getEmail() != null) {
                    user.setEmail(requestDTO.getEmail());
                }
                if (requestDTO.getPassword() != null) {
                    user.setPassword(requestDTO.getPassword());
                }

                userRepository.save(user);

                return "{\"success\": true}";
            } else {
                return "{\"error\": \"Usuário não encontrado\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Erro ao atualizar o usuário\"}";
        }
    }

    public String deleteUser(@PathVariable("id") Long id) {
        try {
            userRepository.deleteById(id);
            return "{\"message\": \"Usuário deletado com sucesso\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Erro ao excluir o usuário\"}";
        }
    }


}
