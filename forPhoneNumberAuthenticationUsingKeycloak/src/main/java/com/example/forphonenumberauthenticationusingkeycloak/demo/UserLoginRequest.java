package com.example.forphonenumberauthenticationusingkeycloak.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

}
