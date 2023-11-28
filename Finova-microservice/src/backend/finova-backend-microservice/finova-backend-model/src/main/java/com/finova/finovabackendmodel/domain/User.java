package com.finova.finovabackendmodel.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String phoneNumber;
    private String image;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
