package com.email.emailapi.model;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;
@Component
@Entity
@Table(name = "user_email")
public class UserEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(name ="email")
    private String email;
    @Column(name ="password")
    private String password;

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEmail{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
