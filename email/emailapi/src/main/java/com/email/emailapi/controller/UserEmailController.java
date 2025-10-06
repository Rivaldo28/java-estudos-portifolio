package com.email.emailapi.controller;

import com.email.emailapi.dto.UserEmailDto;
import com.email.emailapi.model.UserEmail;
import com.email.emailapi.repository.UserEmailRepository;
import com.email.emailapi.service.UserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_email")
public class UserEmailController {

    @Autowired
    private UserEmailRepository userEmailRepository;
    @Autowired
    private UserEmailService userEmailService;


    @GetMapping
    public List<UserEmail> list(){
        return userEmailRepository.findAll();
    }

    @GetMapping("/user_id")
    public List<UserEmailDto> list(String email)
    {
        List<UserEmailDto> userEmailDto = userEmailService.findByUserEmail(email);
        return userEmailDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserEmail userEmail){
        userEmailRepository.save(userEmail);
    }

}
