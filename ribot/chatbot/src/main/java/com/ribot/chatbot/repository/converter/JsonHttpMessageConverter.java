package com.ribot.chatbot.repository.converter;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

@Component
public class JsonHttpMessageConverter extends AbstractHttpMessageConverter<JSONObject> {

    public JsonHttpMessageConverter() {
        super(MediaType.APPLICATION_JSON);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return JSONObject.class.isAssignableFrom(clazz);
    }

    @Override
    protected JSONObject readInternal(Class<? extends JSONObject> clazz, HttpInputMessage inputMessage)
            throws HttpMessageNotReadableException, IOException {
        // Implemente a lógica de conversão de entrada aqui
        return null;
    }

    @Override
    protected void writeInternal(JSONObject jsonObject, HttpOutputMessage outputMessage)
            throws HttpMessageNotWritableException, IOException {
        // Implemente a lógica de conversão de saída aqui
    }
}
