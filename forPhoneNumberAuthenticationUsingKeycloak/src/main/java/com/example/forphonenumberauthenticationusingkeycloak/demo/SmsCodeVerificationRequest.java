package com.example.forphonenumberauthenticationusingkeycloak.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsCodeVerificationRequest {

    @NotBlank
    private String smsCode;

}
