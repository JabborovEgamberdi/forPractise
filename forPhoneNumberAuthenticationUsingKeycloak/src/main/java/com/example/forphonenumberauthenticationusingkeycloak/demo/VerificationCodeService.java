package com.example.forphonenumberauthenticationusingkeycloak.demo;

import com.example.forphonenumberauthenticationusingkeycloak.demo.SmsService;
import com.twilio.exception.TwilioException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationCodeService {

    private final Map<String, String> verificationCodes = new HashMap<>();
    private final SmsService smsService;

    public VerificationCodeService(SmsService smsService) {
        this.smsService = smsService;
    }

    public void generateVerificationCode(String phoneNumber) throws TwilioException {
        String code = String.format("%04d", new Random().nextInt(10000));
        verificationCodes.put(phoneNumber, code);
        smsService.sendSmsCode(phoneNumber, code);
        verifyCode(phoneNumber, code);
    }

    public boolean verifyCode(String phoneNumber, String code) {
        if (verificationCodes.containsKey(phoneNumber) && verificationCodes.get(phoneNumber).equals(code)) {
            verificationCodes.remove(phoneNumber);
            return true;
        }
        return false;
    }

}
