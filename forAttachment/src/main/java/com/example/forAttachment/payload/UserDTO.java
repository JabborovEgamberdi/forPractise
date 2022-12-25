package com.example.forAttachment.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class UserDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private MultipartFile file;

}
