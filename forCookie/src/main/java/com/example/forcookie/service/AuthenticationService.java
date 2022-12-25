package com.example.forcookie.service;

import com.example.forcookie.payload.CredentialsDTO;
import com.example.forcookie.payload.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Service
public class AuthenticationService {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO authenticate(CredentialsDTO credentialsDTO) {
        String encodedMasterPassword = passwordEncoder.encode(CharBuffer.wrap("the-password"));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.getPassword()), encodedMasterPassword)) {
            return new UserDTO(1L ,"Sergio", "Lema", "login", "token");
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDTO findByLogin(String login) {
        if ("login".equals(login)) {
            return new UserDTO(1L ,"Sergio", "Lema", "login", "token");
        }
        throw new RuntimeException("Invalid login");
    }

    public String createToken (UserDTO user) {
        return user.getId() + "&" + user.getLogin() + "&" + calculateHmac(user);
    }

    public UserDTO findByToken(String token) {
        String[] parts = token.split("&");

        Long userId = Long.valueOf(parts[0]);
        String login = parts[1];
        String hmac = parts[2];

        UserDTO user2DTO = findByLogin(login);

        if (!hmac.equals(calculateHmac(user2DTO)) || !userId.equals(user2DTO.getId())) {
            throw new RuntimeException("Invalid Cookie value");
        }

        return user2DTO;
    }

    public String calculateHmac(UserDTO user) {

        byte[] secretKeyBytes = Objects.requireNonNull(secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = Objects.requireNonNull(user.getId() + "&" + user.getLogin()).getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

}
