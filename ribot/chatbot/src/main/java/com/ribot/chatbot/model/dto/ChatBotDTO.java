package com.ribot.chatbot.model.dto;

import javax.persistence.Column;

public class ChatBotDTO {
    private Double codeUser;
    private Boolean activate;
    private Double codeCurrent;
    private Integer codeRelation;
    private Integer codeBefore;
    @Column(unique = true, nullable = true)
    private String input;
    private String output;

    private Integer chatId;

    public Integer getChatId() {return chatId;}

    public void setChatId(Integer chatId) {this.chatId = chatId;}

    public Double getCodeUser() {
        return codeUser;
    }

    public void setCodeUser(Double code_user) {
        this.codeUser = code_user;
    }

    public Boolean getActivate() {return activate;}

    public void setActivate(Boolean activate) {this.activate = activate;}

    public Double getCodeCurrent() {
        return codeCurrent;
    }

    public void setCodeCurrent(Double codeCurrent) {
        this.codeCurrent = codeCurrent;
    }

    public Integer getCodeRelation() {
        return codeRelation;
    }

    public void setCodeRelation(Integer codeRelation) {
        this.codeRelation = codeRelation;
    }

    public Integer getCodeBefore() {
        return codeBefore;
    }

    public void setCodeBefore(Integer codeBefore) {
        this.codeBefore = codeBefore;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}

