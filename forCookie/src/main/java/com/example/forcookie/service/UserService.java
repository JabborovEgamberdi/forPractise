package com.example.forcookie.service;

import com.example.forcookie.payload.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    public ProfileDTO getProfile(Long userId) {
        return new ProfileDTO(new UserSummaryDTO(1L, "Sergio", "Lema"),
                Arrays.asList(new UserSummaryDTO(2L, "John", "Doe")),
                Arrays.asList(new MessageDTO(1L, "My message")),
                Arrays.asList(new ImageDTO(1L, "Title", null)));
    }

    public void addFriend(Long friendId) {
        // nothing to do at the moment
    }

    public List<UserSummaryDTO> searchUsers(String term) {
        return Arrays.asList(new UserSummaryDTO(1L, "Sergio", "Lema"),
                new UserSummaryDTO(2L, "John", "Doe"));
    }

    public UserDTO signUp(SignUpDTO user) {
        return new UserDTO(1L, "Sergio", "Lema", "login", "token");
    }

    public void signOut(UserDTO user) {
        // nothing to do at the moment
    }
}
