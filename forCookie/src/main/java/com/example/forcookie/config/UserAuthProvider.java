package com.example.forcookie.config;

import com.example.forcookie.payload.CredentialsDTO;
import com.example.forcookie.payload.UserDTO;
import com.example.forcookie.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserAuthProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    public UserAuthProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDTO userDTO = null;

        //here we are handle authentication via username and password
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            userDTO = authenticationService.authenticate(new CredentialsDTO(
                    (String) authentication.getPrincipal(),
                    (char[]) authentication.getCredentials()
            ));
        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            // here we handle the authentication based on the cookie value on the cookie token
            authenticationService.findByToken((String) authentication.getPrincipal());
        }

        if (userDTO == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDTO, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
