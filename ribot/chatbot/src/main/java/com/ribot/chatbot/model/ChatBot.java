package com.ribot.chatbot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chatbot")
public class ChatBot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="code_user")
    private Double codeUser;

    @Column(name="activate")
    private Boolean activate;

    @Column(name="code_current")
    private Double codeCurrent;

    @Column(name="full_name")
    private String fullName;

    @Column(name="code_relation")
    private Integer codeRelation;

    @Column(name="code_before")
    private Integer codeBefore;

    @Column(name="input", unique = true, nullable = true)
    private String input;

    @Column(name="output")
    private String output;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;  // Relacionamento com User

    public ChatBot() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCodeUser() {
        return codeUser;
    }

    public void setCodeUser(Double codeUser) {
        this.codeUser = codeUser;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatBot)) return false;
        ChatBot chatBot = (ChatBot) o;
        return id.equals(chatBot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ChatBot(Long id, Double codeUser, Boolean activate, Double codeCurrent, Integer codeRelation, Integer codeBefore, String input, String output) {
        this.id = id;
        this.codeUser = codeUser;
        this.activate = activate;
        this.codeCurrent = codeCurrent;
        this.codeRelation = codeRelation;
        this.codeBefore = codeBefore;
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return "ChatBot{" +
                "id=" + id +
                ", codeUser=" + codeUser +
                ", activate=" + activate +
                ", codeCurrent=" + codeCurrent +
                ", fullName='" + fullName + '\'' +
                ", codeRelation=" + codeRelation +
                ", codeBefore=" + codeBefore +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}
