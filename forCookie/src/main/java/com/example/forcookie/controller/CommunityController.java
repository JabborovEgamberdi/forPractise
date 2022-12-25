package com.example.forcookie.controller;

import com.example.forcookie.payload.ImageDTO;
import com.example.forcookie.payload.MessageDTO;
import com.example.forcookie.payload.UserDTO;
import com.example.forcookie.service.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/community")
public class CommunityController {

    private final CommunityService communityService;


    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getCommunityMessage(
            @AuthenticationPrincipal UserDTO user,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(communityService.getCommunityMessage(user, page));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDTO>> getCommunityImages(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(communityService.getCommunityImages(page));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDTO> postMessage(@RequestParam MessageDTO messageDTO) {
        return ResponseEntity.created(URI.create("/v1/community/messages"))
                .body(communityService.postMessage(messageDTO));
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDTO> postImage(
            @RequestParam MultipartFile file,
            @RequestParam(value = "title") String title) {
        return ResponseEntity.created(URI.create("/v1/community/images"))
                .body(communityService.postImage(file, title));
    }

}
