package com.example.forcookie.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User2DTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String token;

    public User2DTO() {
        super();
    }
}
