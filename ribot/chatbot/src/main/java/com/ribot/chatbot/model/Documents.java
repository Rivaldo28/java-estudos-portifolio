package com.ribot.chatbot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "documents")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cep")
    private String cep;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "cpf")
    private Long cpf;

    @Column(name = "cnpj")
    private Long cnpj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Documents)) return false;
        Documents documents = (Documents) o;
        return id.equals(documents.id) && Objects.equals(nome, documents.nome) && Objects.equals(email, documents.email) && Objects.equals(telefone, documents.telefone) && Objects.equals(cep, documents.cep) && Objects.equals(endereco, documents.endereco) && Objects.equals(bairro, documents.bairro) && Objects.equals(numero, documents.numero) && Objects.equals(cpf, documents.cpf) && Objects.equals(cnpj, documents.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, telefone, cep, endereco, bairro, numero, cpf, cnpj);
    }
}
