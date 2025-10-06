package com.ribot.chatbot.controller;

import com.ribot.chatbot.model.User;
import com.ribot.chatbot.model.dto.UserRequestDTO;
import com.ribot.chatbot.repository.UserRepository;
import com.ribot.chatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/user")//pre-path
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> list(){
        return userService.list();
    }


    @PostMapping("/insert")
    public User insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody UserRequestDTO requestDTO) {
        return userService.updateUser(requestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

}
