package com.example.forcookie.controller;

import com.example.forcookie.config.CookieAuthenticationFilter;
import com.example.forcookie.payload.SignUpDTO;
import com.example.forcookie.payload.UserDTO;
import com.example.forcookie.service.AuthenticationService;
import com.example.forcookie.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<UserDTO> signIn(@AuthenticationPrincipal UserDTO user,
                                          HttpServletResponse response) {

        Cookie cookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME,
                authenticationService.createToken(user));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(Duration.of(1, ChronoUnit.DAYS).toSecondsPart());
        cookie.setPath("/");

        response.addCookie(cookie);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody @Valid SignUpDTO user) {
        UserDTO createdUser = userService.signUp(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId() + "/profile")).body(createdUser);
    }


    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        SecurityContextHolder.clearContext();

        Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> CookieAuthenticationFilter.COOKIE_NAME
                        .equals(cookie.getName()))
                .findFirst();

        authCookie.ifPresent(cookie -> cookie.setMaxAge(0));

        return ResponseEntity.noContent().build();
    }


}
