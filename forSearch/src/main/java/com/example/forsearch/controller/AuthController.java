package com.example.forsearch.controller;


import com.example.forsearch.config.CurrentUser;
import com.example.forsearch.config.JwtProvider;
import com.example.forsearch.entity.forSecurity.User;
import com.example.forsearch.payload.forSecurity.LoginDTO;
import com.example.forsearch.payload.forSecurity.RegisterDTO;
import com.example.forsearch.repository.forSecurity.UserRepository;
import com.example.forsearch.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        var response = authService.login(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @GetMapping("/me")
    public HttpEntity<?> getMe(@CurrentUser User user) { //Parametr
        return ResponseEntity.ok().body("Mana" + " " + user.getUsername() + " " + user.getRoles().getName());
    }

    @GetMapping("/ketmon")
    public ResponseEntity<?> ketmon() {
        return ResponseEntity.status(200).body("Ketmon");
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        var response = authService.register(registerDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @PutMapping("/editRole/{id}")
    public ResponseEntity<?> changeRole(@PathVariable Integer id, @CurrentUser User user) {
        var response = authService.editRole(id, user);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

}