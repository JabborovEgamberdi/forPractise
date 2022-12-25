package com.example.forcookie.service;

import com.example.forcookie.payload.ImageDTO;
import com.example.forcookie.payload.MessageDTO;
import com.example.forcookie.payload.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class CommunityService {

    public List<MessageDTO> getCommunityMessage(UserDTO user, int page) {
        return Arrays.asList(
                new MessageDTO(1L, "First message " + user.getFirstName()),
                new MessageDTO(2L, "Second message" + user.getFirstName()));
    }

    public List<ImageDTO> getCommunityImages(int page) {
        return Arrays.asList(
                new ImageDTO(1L, "First title ", null),
                new ImageDTO(2L, "Second title", null)
        );
    }

    public MessageDTO postMessage (MessageDTO messageDTO) {
        return new MessageDTO(3L, "New message");
    }

    public ImageDTO postImage(MultipartFile file, String title) {
        return new ImageDTO(3L, "New title", null);
    }

}
