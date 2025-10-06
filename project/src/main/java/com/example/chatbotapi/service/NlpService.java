package com.example.chatbotapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class NlpService {
    
    private TokenizerME tokenizer;
    private DocumentCategorizerME categorizer;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\+?[0-9]{10,15}");
    
    @PostConstruct
    public void init() {
        try {
            // Load tokenizer model
            InputStream tokenizerModelIn = getClass().getResourceAsStream("/models/en-token.bin");
            if (tokenizerModelIn != null) {
                TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelIn);
                tokenizer = new TokenizerME(tokenizerModel);
                tokenizerModelIn.close();
            } else {
                // Create a simple tokenizer if model is not available
                log.warn("Tokenizer model not found, using simple tokenizer");
                tokenizer = null;
            }
            
            // Load categorizer model
            InputStream categorizerModelIn = getClass().getResourceAsStream("/models/en-doccat.bin");
            if (categorizerModelIn != null) {
                DoccatModel categorizerModel = new DoccatModel(categorizerModelIn);
                categorizer = new DocumentCategorizerME(categorizerModel);
                categorizerModelIn.close();
            } else {
                // Create a simple categorizer if model is not available
                log.warn("Categorizer model not found, using simple categorizer");
                categorizer = null;
            }
            
        } catch (IOException e) {
            log.error("Error loading NLP models", e);
        }
    }
    
    public String[] tokenize(String text) {
        if (tokenizer != null) {
            return tokenizer.tokenize(text);
        } else {
            // Simple tokenization by whitespace
            return text.split("\\s+");
        }
    }
    
    public String categorize(String text) {
        if (categorizer != null) {
            String[] tokens = tokenize(text);
            double[] outcomes = categorizer.categorize(tokens);
            return categorizer.getBestCategory(outcomes);
        } else {
            // Simple intent detection based on keywords
            text = text.toLowerCase();
            if (text.contains("hello") || text.contains("hi")) {
                return "greeting";
            } else if (text.contains("bye") || text.contains("goodbye")) {
                return "goodbye";
            } else if (text.contains("help")) {
                return "help";
            } else if (text.contains("contact") || text.contains("talk") || text.contains("agent")) {
                return "contact";
            } else {
                return "unknown";
            }
        }
    }
    
    public Map<String, String> extractCustomerData(String text) {
        Map<String, String> extractedData = new HashMap<>();
        
        // Extract email
        Matcher emailMatcher = EMAIL_PATTERN.matcher(text);
        if (emailMatcher.find()) {
            extractedData.put("email", emailMatcher.group());
        }
        
        // Extract phone number
        Matcher phoneMatcher = PHONE_PATTERN.matcher(text);
        if (phoneMatcher.find()) {
            extractedData.put("phoneNumber", phoneMatcher.group());
        }
        
        return extractedData;
    }
    
    public boolean containsCustomerData(String text) {
        return EMAIL_PATTERN.matcher(text).find() || PHONE_PATTERN.matcher(text).find();
    }
    
    public String generateResponse(String intent, Map<String, String> extractedData) {
        switch (intent) {
            case "greeting":
                return "Hello! How can I help you today?";
            case "goodbye":
                return "Thank you for chatting with us. Have a great day!";
            case "help":
                return "I can help you with various tasks. Just let me know what you need assistance with.";
            case "contact":
                if (extractedData.isEmpty()) {
                    return "I'd be happy to connect you with someone. Could you please provide your email or phone number?";
                } else {
                    return "Thank you for providing your contact information. A representative will get in touch with you soon.";
                }
            default:
                if (!extractedData.isEmpty()) {
                    return "Thank you for providing your information. Is there anything specific you'd like help with?";
                } else {
                    return "I'm not sure I understand. Could you please rephrase your question?";
                }
        }
    }
}