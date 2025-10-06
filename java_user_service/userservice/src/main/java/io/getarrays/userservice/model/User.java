package io.getarrays.userservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String username;

    private String password;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();


}
