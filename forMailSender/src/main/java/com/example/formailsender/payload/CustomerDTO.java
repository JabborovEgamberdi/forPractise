package com.example.formailsender.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomerDTO {

    @NotNull
    private String fullName;

    @NotNull
    @Email
    private String mail;

    @NotNull
    @Size(min = 5)
    private String comment;

}
