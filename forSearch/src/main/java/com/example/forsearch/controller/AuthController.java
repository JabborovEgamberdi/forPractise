package com.example.forsearch.controller;


import com.example.forsearch.config.CurrentUser;
import com.example.forsearch.config.JwtProvider;
import com.example.forsearch.entity.forSecurity.User;
import com.example.forsearch.payload.forSecurity.LoginDTO;
import com.example.forsearch.payload.forSecurity.RegisterDTO;
import com.example.forsearch.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        var response = authService.login(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @GetMapping("/me")
    public HttpEntity<?> getMe(@CurrentUser User user) { //Parametr
        return ResponseEntity.ok().body("Mana" + user);
    }

    @GetMapping("/ketmon")
    public ResponseEntity<?> ketmon() {
        return ResponseEntity.status(200).body("Ketmon");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        var response = authService.register(registerDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }
}
