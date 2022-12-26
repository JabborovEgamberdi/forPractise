package com.example.forsearch.config;

import com.example.forsearch.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//har bir requestdan oldin kim kiryapti
@Configuration
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
//
//    public JwtFilter(JwtProvider jwtProvider, AuthService authService) {
//        this.jwtProvider = jwtProvider;
//        this.authService = authService;
//    }

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            authorization = authorization.substring(7);
            String phoneNumber = jwtProvider.getUsernameFromToken(authorization);
            if (phoneNumber != null) {
                UserDetails user = authService.loadUserByUsername(phoneNumber);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }

        }
        filterChain.doFilter(request, response);
    }
}
