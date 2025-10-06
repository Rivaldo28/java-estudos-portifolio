package com.ribot.chatbot.service;

import com.ribot.chatbot.model.ChatBot;
import com.ribot.chatbot.model.User;
import com.ribot.chatbot.model.dto.ChatBotDTO;
import com.ribot.chatbot.repository.ChatBotRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ChatBotService {

    @Autowired
    private ChatBotRepository chatBotRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private CodDataService codDataService;


    @Autowired
    private UserService userService;

    @GetMapping
    public List<ChatBot> list(){
        return chatBotRepository.findAll();
    }

    public ResponseEntity<Object> insert(@RequestBody ChatBotDTO requestDTO) {
        try {
            JSONObject objJSON = new JSONObject();
            objJSON.put("code_user", requestDTO.getCodeUser() != null ? requestDTO.getCodeUser() : "0");
            objJSON.put("activate", requestDTO.getActivate() != null ? requestDTO.getActivate() : "1");
            objJSON.put("code_current", requestDTO.getCodeCurrent() != null ? requestDTO.getCodeCurrent() :
                    String.valueOf(codDataService.cod()));
//            objJSON.put("code_current", requestDTO.getCodeCurrent() != null ? requestDTO.getCodeCurrent() : null);
            objJSON.put("code_relation", requestDTO.getCodeRelation() != null ? requestDTO.getCodeRelation() : "0");
            objJSON.put("code_before", requestDTO.getCodeBefore() != null ? requestDTO.getCodeBefore() : "0");
            objJSON.put("input", requestDTO.getInput() != null ? requestDTO.getInput() : "");
            objJSON.put("output", requestDTO.getOutput() != null ? requestDTO.getOutput() : "Desculpe, mas não entendi.");

            ResponseEntity<Object> result = insertData(objJSON);
            return result;
        } catch (Exception e) {
            System.out.println("{error: 'erro de requisição 18'}");
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Object> insertData(@RequestBody JSONObject objJSON) {
        try {
            String input = objJSON.optString("input", null);

            if (input == null || input.isEmpty()) {
                JSONObject response = new JSONObject();
                response.put("message", "O campo 'input' não pode estar vazio.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
            }

            if (chatBotRepository.existsByInput(input)) {
                JSONObject response = new JSONObject();
                response.put("message", "Esses dados já existem no banco!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
            }

//            Double codeUser = objJSON.optDouble("code_user", 0.0);
//            System.out.println("Buscando usuário com code_user: " + codeUser);
//            System.out.println("Dados recebidos: " + objJSON.toString());
            String codeUserString = objJSON.optString("code_user", "0");
            Double codeUser = Double.parseDouble(codeUserString);

            System.out.println("Buscando usuário com code_user: " + codeUser);
            System.out.println("Dados recebidos: " + objJSON.toString());



            User user = userService.findByCodeUser(codeUser);
            if (user == null) {
                JSONObject response = new JSONObject();
                response.put("message", "Usuário não encontrado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
            }

            ChatBot chatBot = new ChatBot();
            chatBot.setCodeUser(codeUser);
            chatBot.setActivate(objJSON.optBoolean("activate", false));
            chatBot.setCodeCurrent(objJSON.optDouble("code_current", codDataService.cod()));
            chatBot.setCodeRelation(objJSON.optInt("code_relation", 0));
            chatBot.setCodeBefore(objJSON.optInt("code_before", 0));
            chatBot.setInput(input);
            chatBot.setOutput(objJSON.optString("output", ""));
            chatBot.setUser(user); // Atribuindo o usuário

            chatBotRepository.save(chatBot);

            return ResponseEntity.status(HttpStatus.OK).body(objJSON.toString());
        } catch (DataIntegrityViolationException e) {
            JSONObject response = new JSONObject();
            response.put("message", "Erro ao inserir dados no banco de dados: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("message", "Erro inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
        }
    }


    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody ChatBotDTO requestDTO) {
        JSONObject objJSON = new JSONObject();
        objJSON.put("codeUser", requestDTO.getCodeUser() != null ? requestDTO.getCodeUser() : 0);
        objJSON.put("activate", requestDTO.getActivate() != null ? requestDTO.getActivate() : false);
        objJSON.put("codeCurrent", requestDTO.getCodeCurrent() != null ?
                requestDTO.getCodeCurrent() : codDataService.cod());
        objJSON.put("codeRelation", requestDTO.getCodeRelation() != null ? requestDTO.getCodeRelation() : 0);
        objJSON.put("codeBefore", requestDTO.getCodeBefore() != null ? requestDTO.getCodeBefore() : 0);
        objJSON.put("input", requestDTO.getInput() != null ? requestDTO.getInput() : "");
        objJSON.put("output", requestDTO.getOutput() != null ? requestDTO.getOutput() : "Desculpe mas não entendi.");
        objJSON.put("chatId", requestDTO.getChatId() != null ? requestDTO.getChatId() : 0);
        try {
            String sql = "UPDATE Chatbot SET codeUser = ?, activate = ?, " +
                    "codeCurrent = ?, codeRelation = ?, codeBefore = ?, input = ?, output = ?, chatId = ? WHERE id = ?";
            jdbcTemplate.update(sql, objJSON.getInt("codeUser"), objJSON.getBoolean("activate"),
                    objJSON.getDouble("codeCurrent"), objJSON.getInt("codeRelation"),
                    objJSON.getInt("codeBefore"), objJSON.getString("input"),
                    objJSON.getString("output"), objJSON.getInt("chatId"), id);


            return ResponseEntity.status(HttpStatus.OK).body(objJSON.toString());
        } catch (DataIntegrityViolationException e) {
            JSONObject response = new JSONObject();
            response.put("message", "Erro ao atualizar os dados no banco!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }
    }

    public ResponseEntity<Object> delete(@PathVariable int id) {
        String sql = "DELETE FROM chatbot WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected > 0) {
            JSONObject response = new JSONObject();
            response.put("message", "Registro " + id + " deletado com sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            JSONObject response = new JSONObject();
            response.put("message", "O ID " + id + " não existe no banco de dados.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }

    }

    public ResponseEntity<List<String>> findByInput(ChatBotDTO searchRequest) {
        String input = searchRequest.getInput();
        List<String> result = chatBotRepository.findOutputByInput(input);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("Sua pergunta não foi inteligente seu burro!!"));
        }

        return ResponseEntity.ok(result);
    }

    public List<ChatBot> findByChatId(Integer chatId) {
        return chatBotRepository.findByChatId(chatId);
    }


}
