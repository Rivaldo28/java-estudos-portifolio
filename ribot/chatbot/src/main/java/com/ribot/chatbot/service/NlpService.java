package com.ribot.chatbot.service;

import com.ribot.chatbot.model.ChatBot;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.Normalizer;
import java.util.*;
import java.util.function.Consumer;
import com.google.gson.Gson;

@Service
public class NlpService {

    private static DocumentsService documentsService;
    public static List<Map<String, Object>> nlp(String question, List<Map<String, Object>> array, int code_user) {
        String originalQuestion = question.trim();
        try {
            int findInput = 0;
            int findIndex = 0;

            List<Map<String, Object>> documents = DocumentsService.getDocuments(originalQuestion, code_user);
            if (documents != null) {
                return List.of(Map.of(
                        "_id", "0",
                        "code_user", code_user,
                        "activate", 1,
                        "code_current", -1,
                        "code_relation", -1,
                        "code_before", -1,
                        "input", originalQuestion,
                        "output", "Ok! Entendido."
                ));
            } else {
                for (int i = 0; i < array.size(); i++) {
                    question = question.trim();
                    String input = array.get(i).get("input").toString().trim();
                    if (input.length() <= 0) {
                        input = array.get(i).get("output").toString().trim();
                    }
                    question = normalizeString(question).toLowerCase();
                    input = normalizeString(input).toLowerCase();
                    question = question.replaceAll("[^a-zA-Z0-9\\s]", "");
                    input = input.replaceAll("[^a-zA-Z0-9\\s]", "");

                    String[] tokenizationQuestion = question.split(" ");
                    String[] tokenizationInput = input.split(" ");

                    for (int x = 0; x < tokenizationQuestion.length; x++) {
                        tokenizationQuestion[x] = tokenizationQuestion[x].length() > 3
                                ? tokenizationQuestion[x].substring(0, tokenizationQuestion[x].length() - 3)
                                : tokenizationQuestion[x];
                    }
                    for (int x = 0; x < tokenizationInput.length; x++) {
                        tokenizationInput[x] = tokenizationInput[x].length() > 3
                                ? tokenizationInput[x].substring(0, tokenizationInput[x].length() - 3)
                                : tokenizationInput[x];
                    }

                    int words = 0;
                    for (String token : tokenizationQuestion) {
                        if (Arrays.asList(tokenizationInput).contains(token)) {
                            words++;
                        }
                    }

                    if (words > findInput) {
                        findInput = words;
                        findIndex = i;
                    }
                }

                if (findInput > 0) {
                    return List.of(Map.of(
                            "_id", array.get(findIndex).get("_id"),
                            "code_user", array.get(findIndex).get("code_user"),
                            "activate", array.get(findIndex).get("activate"),
                            "code_current", array.get(findIndex).get("code_current"),
                            "code_relation", array.get(findIndex).get("code_relation"),
                            "code_before", array.get(findIndex).get("code_before"),
                            "input", originalQuestion,
                            "output", array.get(findIndex).get("output")
                    ));
                } else {
                    return List.of(Map.of(
                            "_id", "0",
                            "code_user", array.get(findIndex).get("code_user"),
                            "activate", array.get(findIndex).get("activate"),
                            "code_current", array.get(findIndex).get("code_current"),
                            "code_relation", array.get(findIndex).get("code_relation"),
                            "code_before", array.get(findIndex).get("code_before"),
                            "input", originalQuestion,
                            "output", "Desculpe, mas não sei te responder."
                    ));
                }
            }
        } catch (Exception e) {
            return List.of(Map.of(
                    "_id", "0",
                    "code_user", code_user,
                    "activate", 0,
                    "code_current", 0,
                    "code_relation", 0,
                    "code_before", 0,
                    "input", originalQuestion,
                    "output", "Desculpe, mas não sei te responder."
            ));
        }
    }

    private static String normalizeString(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    public static String abreviacoes(String input) {
        try {
            String result = input.trim();
            result = result.replace(" vc ", "você");
            result = result.replace(" tb ", "também");
            result = result.replace(" oq ", "o que");
            result = result.replace(" dq ", "de que");
            result = result.replace(" td ", "tudo");
            result = result.replace(" pq ", "por quê");
            result = result.trim();
            return result;
        } catch (Exception e) {
            System.out.println("Error executing abbreviation function: " + e.getMessage());
            return input;
        }
    }

    public Object questionData(ChatBot chatBot) {
        try {
            String chat = "chatbot";
            double codeUser = chatBot.getCodeUser();
            boolean activate = chatBot.getActivate();
            int codeBefore = chatBot.getCodeBefore();
            String input = chatBot.getInput();

            String query;
            PreparedStatement statement;
            ResultSet result;

            Connection connection = null;

            if (codeBefore > 0) {
                query = "SELECT * FROM " + chat + " WHERE code_user = ? AND code_relation = ?";
                statement = connection.prepareStatement(query);
                statement.setDouble(1, codeUser);
                statement.setInt(2, codeBefore);
                result = statement.executeQuery();

                if (!result.next()) {
                    query = "SELECT * FROM " + chat + " WHERE code_user = ?";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1, codeUser);
                    result = statement.executeQuery();

                    if (!result.next()) {
                        String questionUser = abreviacoes(input);
                        query = "SELECT * FROM " + chat + " WHERE code_user = ?";
                        statement = connection.prepareStatement(query);
                        statement.setDouble(1, codeUser);
                        result = statement.executeQuery();

//                        List<Map<String, Object>> resultList = nlp(questionUser, resultSetToList(result), codeUser);
                        int codeUserInt = (int) codeUser;
                        List<Map<String, Object>> resultList = nlp(questionUser, resultSetToList(result), codeUserInt);
                        // Faça o que for necessário com a lista de resultados (resultList)
                        return resultList;

                    } else {
                        String questionUser = abreviacoes(input);
                        result.beforeFirst();
//                        List<Map<String, Object>> resultList = nlp(questionUser, resultSetToList(result), codeUser);
                        int codeUserInt = (int) codeUser;
                        List<Map<String, Object>> resultList = nlp(questionUser, resultSetToList(result), codeUserInt);
                        // Faça o que for necessário com a lista de resultados (resultList)
                        return resultList;
                    }
                } else {
                    List<Map<String, Object>> resultList = new ArrayList<>();
                    // Faça o que for necessário com a lista de resultados (resultList)
                    return resultList;
                }
            } else {
                query = "SELECT * FROM " + chat + " WHERE code_user = ?";
                statement = connection.prepareStatement(query);
                statement.setDouble(1, codeUser);
                result = statement.executeQuery();

                if (!result.next()) {
                    String questionUser = abreviacoes(input);
                    query = "SELECT * FROM " + chat + " WHERE code_user = ?";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1, codeUser);
                    result = statement.executeQuery();

//                    List<Map<String, Object>> resultList = nlp(questionUser, resultSetToList(result), codeUser);
                    int codeUserInt = (int) codeUser;
                    List<Map<String, Object>> resultList = nlp(questionUser, resultSetToList(result), codeUserInt);
                    // Faça o que for necessário com a lista de resultados (resultList)
                    return resultList;

                } else {
                    List<Map<String, Object>> resultList = new ArrayList<>();
                    // Faça o que for necessário com a lista de resultados (resultList)
                    return resultList;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
            // Trate o erro de alguma forma apropriada, como registrar ou relançar a exceção
            // Retorne um valor padrão, caso apropriado
            return new ArrayList<>(); // ou qualquer outro valor padrão adequado ao seu caso
        }
    }
    public List<Map<String, Object>> resultSetToList(ResultSet result) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (result.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = result.getObject(i);
                row.put(columnName, value);
            }
            resultList.add(row);
        }

        return resultList;
    }

}
