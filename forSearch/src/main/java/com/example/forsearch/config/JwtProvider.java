package com.example.forsearch.config;

import com.example.forsearch.repository.forSecurity.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Autowired
    private UserRepository userRepository;
    private final String secretKey = "asssalomu";

    public String generateToken(String phoneNumber) {
        long expireTime = 30000L * 86400;
        System.out.println(new Date(System.currentTimeMillis() + expireTime));
        String token = Jwts
                .builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .claim("role", userRepository.findByPhoneNumber(phoneNumber).get().getRoles())
                .compact();
        return token;
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }


}
