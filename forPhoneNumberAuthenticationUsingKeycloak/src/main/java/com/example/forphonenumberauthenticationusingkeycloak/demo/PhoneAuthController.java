package com.example.forphonenumberauthenticationusingkeycloak.demo;

import com.example.forphonenumberauthenticationusingkeycloak.user.User;
import com.example.forphonenumberauthenticationusingkeycloak.user.UserRepository;
import com.twilio.exception.TwilioException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class PhoneAuthController {

    private final UserRepository userRepository;

    private final VerificationCodeService verificationCodeService;

    private final SmsService smsService;

    private final Map<String, String> verificationCodes = new HashMap<>();


    public PhoneAuthController(UserRepository userRepository, VerificationCodeService verificationCodeService, SmsService smsService) {
        this.userRepository = userRepository;
        this.verificationCodeService = verificationCodeService;
        this.smsService = smsService;
    }

    @PostMapping("/send-sms-code")
    public ResponseEntity<?> sendSmsCode(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // Retrieve the user from the database
        User user = userRepository.findByPhoneNumber(userLoginRequest.getPhoneNumber());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid phone number");
        }
        // Verify the user's password
        if (!BCrypt.checkpw(userLoginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        // Send the SMS code to the user's phone number
        String smsCode = sendSmsCode(user.getPhoneNumber());
        // Save the SMS code in the user's session
        HttpSession session = request.getSession();
        session.setAttribute("smsCode", smsCode);
        session.setAttribute("phoneNumber", user.getPhoneNumber());
        // Return the access token
        return ResponseEntity.ok(getAccessToken(request, user));
    }

    @PostMapping("/verify-sms-code")
    public ResponseEntity<?> verifySmsCode(@RequestBody SmsCodeVerificationRequest smsCodeVerificationRequest,
                                           HttpServletRequest request) {
        // Retrieve the SMS code and the phone number from the user's session
        HttpSession session = request.getSession();
        String smsCode = (String) session.getAttribute("smsCode");
        String phoneNumber = (String) session.getAttribute("phoneNumber");
        // Check if the SMS code is correct
        if (smsCodeVerificationRequest.getSmsCode().equals(smsCode)) {
            // Retrieve the user from the database
            User user = userRepository.findByPhoneNumber(phoneNumber);
            // Return the access token
            return ResponseEntity.ok(getAccessToken(request, user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid SMS code");
        }
    }

    private String sendSmsCode(String phoneNumber) {
        // TODO: Implement SMS code sending logic
        generateVerificationCode(phoneNumber);
        return "123456";
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

    private String getAccessToken(HttpServletRequest request, User user) {
        // Get the Keycloak security context
        KeycloakSecurityContext context =
                (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        // Create the JWT token
        JwtBuilder builder = Jwts.builder()
//                .setIssuer(context.getDeployment().getRealm())
                .setIssuer(context.getRealm())
                .setSubject(user.getPhoneNumber())
                .claim("email", user.getEmail())
                .claim("phoneNumber", user.getPhoneNumber())
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString("my-secret-key".getBytes()));
        // Set the expiration time to 10 minutes
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + (10 * 60 * 1000);
        builder.setExpiration(new Date(expMillis));
        // Build the JWT token
        String token = builder.compact();
        return token;
    }
}