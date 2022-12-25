package com.example.forcookie.controller;

import com.example.forcookie.payload.ProfileDTO;
import com.example.forcookie.payload.UserSummaryDTO;
import com.example.forcookie.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileDTO> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PostMapping("/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long friendId) {
        userService.addFriend(friendId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/search")
    public ResponseEntity<List<UserSummaryDTO>> searchUsers(@RequestParam(value = "term") String term) {
        return ResponseEntity.ok(userService.searchUsers(term));
    }

}
