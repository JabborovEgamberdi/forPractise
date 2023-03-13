package com.example.forphonenumberauthenticationusingkeycloak.demo;

import com.twilio.exception.TwilioException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final TwilioRestClient twilioRestClient;
    private final String twilioPhoneNumber;

    public SmsService(@Value("${twilio.account-sid}") String accountSid,
                      @Value("${twilio.auth-token}") String authToken,
                      @Value("${twilio.phone-number}") String twilioPhoneNumber) {
        this.twilioRestClient = new TwilioRestClient.Builder(accountSid, authToken).build();
        this.twilioPhoneNumber = twilioPhoneNumber;
    }

    public void sendSmsCode(String phoneNumber, String code) throws TwilioException {
        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioPhoneNumber);
        String message = "Your verification code is: " + code;

        Message smsMessage = Message.creator(to, from, message).create(twilioRestClient);
        System.out.println("SMS message SID: " + smsMessage.getSid());
    }

}
