package com.email.emailapi.controller;

import com.email.emailapi.model.EmailRequest;
import com.email.emailapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/welcome")
    public String welcome(){
        return "Olá esse é meu email";
    }

    @RequestMapping(value = "/sendmail", method = RequestMethod.POST)
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request){

        System.out.println(request);
        boolean result = this.emailService.sendEmail(request.getTo(), request.getSubject(), request.getMessage());
        if(result){
            return ResponseEntity.ok("Email enviado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email não enviado");
        }

    }

}
