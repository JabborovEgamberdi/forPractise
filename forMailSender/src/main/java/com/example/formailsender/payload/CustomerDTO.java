package com.example.formailsender.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
