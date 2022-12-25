package com.example.forcookie.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignUpDTO {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String login;

    @NotEmpty
    private char[] password;


    public SignUpDTO() {
        super();
    }

    public SignUpDTO(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String login, @NotEmpty char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }


}
