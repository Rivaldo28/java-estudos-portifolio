package com.ribot.chatbot.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CodDataService {

    public long cod() {
        LocalDateTime data = LocalDateTime.now();
        int ano = data.getYear();
        int mes = data.getMonthValue();
        int dia = data.getDayOfMonth();
        int hora = data.getHour();
        int minuto = data.getMinute();
        int segundo = data.getSecond();
        int milesegundo = data.getNano() / 1000000;
        long result = Long.parseLong(ano + "" + mes + "" + dia + "" + hora + "" + minuto + "" + segundo
                + "" + milesegundo) / 2;
        return result;
    }


}
