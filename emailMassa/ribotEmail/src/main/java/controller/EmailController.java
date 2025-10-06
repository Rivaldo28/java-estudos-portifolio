package controller;

import model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.EmailService;

import javax.validation.Valid;


@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Olá, este é o meu endpoint de envio de e-mail!";
    }

//    @RequestMapping(value = "/sendmail", method = RequestMethod.POST)
//    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request){
//
//        System.out.println(request);
//        boolean result = this.emailService.sendEmail(request.getTo(), request.getSubject(), request.getMessage());
//        if(result){
//            return ResponseEntity.ok("Email enviado com sucesso");
//        }else{
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email não enviado");
//        }
//
//    }

    @PostMapping("/sendmail")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            System.out.println(request);
            boolean result = this.emailService.sendEmail(request.getTo(), request.getSubject(), request.getMessage());
            if(result){
                return ResponseEntity.ok("Email enviado com sucesso");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email não enviado");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email não enviado: " + ex.getMessage());
        }
    }

}
