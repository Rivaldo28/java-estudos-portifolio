package com.ribot.chatbot.controller;

import com.ribot.chatbot.model.ChatBot;
import com.ribot.chatbot.service.ChatBotService;
import com.ribot.chatbot.model.dto.ChatBotDTO;
import com.ribot.chatbot.repository.ChatBotRepository;
import com.ribot.chatbot.service.NlpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;


@RestController
@RequestMapping(path = "chat", produces = MediaType.APPLICATION_JSON_VALUE)//pre-path
public class ChatBotController {

//    @Autowired
//    private JsonHttpMessageConverter jsonHttpMessageConverter;

     private Connection connection;

    @Autowired
    private ChatBotRepository chatBotRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ChatBotService chatBotService;

    @Autowired
    private NlpService nlpService;

    @GetMapping
    public List<ChatBot> list(){
        return chatBotService.list();
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody ChatBotDTO requestDTO) {
        return chatBotService.insert(requestDTO );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody ChatBotDTO requestDTO) {
        return chatBotService.update(id, requestDTO );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        return chatBotService.delete(id);
    }


    @GetMapping(value = "/find")
    public ResponseEntity<List<String>> findByInput(@RequestBody ChatBotDTO searchRequest) {
        return chatBotService.findByInput(searchRequest);
    }

    @GetMapping("/question")
    public ResponseEntity<Object> questionData(
            @RequestParam(value = "code_user", required = false) Double codeUser,
            @RequestParam(value = "activate", required = false, defaultValue = "1") Boolean activate,
            @RequestParam(value = "code_before", required = false) Integer codeBefore,
            @RequestParam(value = "input", required = false, defaultValue = "") String input) {

        try {
            // Crie um objeto ChatBot com os parâmetros recebidos
            ChatBot questionDataInput = new ChatBot();
            questionDataInput.setCodeUser(codeUser != null ? codeUser : 0);
            questionDataInput.setActivate(activate);
            questionDataInput.setCodeBefore(codeBefore != null ? codeBefore : 0);
            questionDataInput.setInput(input);
            questionDataInput.setCodeCurrent(codeUser);
            System.out.println("Dados recebidos: " + questionDataInput);


            // Chame o serviço e obtenha o resultado
            Object result = nlpService.questionData(questionDataInput);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println("Error handling question request: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro de requisição");
        }
    }

    @GetMapping("/code-user/{chatId}")
    public List<ChatBot> getChatBotsByChatId(@PathVariable Integer chatId) {
        return chatBotService.findByChatId(chatId);
    }

}
