package com.email.emailapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Getter
@Setter
@JsonIgnoreProperties({"id"})
@Component
public class UserEmailDto {

    private String email;

    private String password;

    public UserEmailDto() {
    }

    public UserEmailDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
