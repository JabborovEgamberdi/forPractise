package com.example.forcookie.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CredentialsDTO {

    private String login;

    private char[] password;

    public CredentialsDTO(){
        super();
    }

}
