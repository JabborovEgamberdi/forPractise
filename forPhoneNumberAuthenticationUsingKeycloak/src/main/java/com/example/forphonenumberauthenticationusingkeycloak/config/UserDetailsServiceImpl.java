package com.example.forphonenumberauthenticationusingkeycloak.config;

import com.example.forphonenumberauthenticationusingkeycloak.user.User;
import com.example.forphonenumberauthenticationusingkeycloak.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with phone number: " + phoneNumber);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(), user.getPassword(), new ArrayList<>());
    }
}
