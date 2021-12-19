package com.example.springproj9;

import com.sun.istack.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {}
}