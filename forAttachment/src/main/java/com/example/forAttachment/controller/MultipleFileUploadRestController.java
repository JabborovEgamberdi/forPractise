package com.example.forAttachment.controller;

import com.example.forAttachment.entity.user.AttachmentContentUser;
import com.example.forAttachment.entity.user.AttachmentUser;
import com.example.forAttachment.entity.user.User;
import com.example.forAttachment.payload.ApiResponse;
import com.example.forAttachment.payload.UserDTO;
import com.example.forAttachment.repository.user.UserAttachmentContentRepository;
import com.example.forAttachment.repository.user.UserAttachmentRepository;
import com.example.forAttachment.repository.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class MultipleFileUploadRestController {

    private final UserRepository userRepository;
    private final UserAttachmentRepository userAttachmentRepository;
    private final UserAttachmentContentRepository userAttachmentContentRepository;

    private static final Logger logger =
            Logger.getLogger(MultipleFileUploadRestController.class.getName());

    public MultipleFileUploadRestController(
            UserRepository userRepository,
            UserAttachmentRepository userAttachmentRepository,
            UserAttachmentContentRepository userAttachmentContentRepository
    ) {
        this.userRepository = userRepository;
        this.userAttachmentRepository = userAttachmentRepository;
        this.userAttachmentContentRepository = userAttachmentContentRepository;
    }

    @PostMapping("/upload/multiple/files")
    public ResponseEntity<String> uploadData(
            @RequestParam("multipleFiles") MultipartFile[] files
    ) throws Exception {

        if (files == null || files.length == 0) {
            throw new RuntimeException("You must select at least one file for uploading");
        }

        StringBuilder stringBuilder = new StringBuilder(files.length);

        for (int i = 0; i < files.length; i++) {
            InputStream inputStream = files[i].getInputStream();
            String originalName = files[i].getOriginalFilename();
            String name = files[i].getName();
            String contentType = files[i].getContentType();
            long size = files[i].getSize();

            stringBuilder.append("File Name: ").append(originalName).append("\n");

            logger.info("InputStream: " + inputStream);
            logger.info("OriginalName: " + originalName);
            logger.info("Name: " + name);
            logger.info("ContentType: " + contentType);
            logger.info("Size: " + size);
        }

        return new ResponseEntity<String>(stringBuilder.toString(), HttpStatus.OK);
    }

    @PostMapping("/upload/multiple/files/database")
    public HttpEntity<String> uploadDataToDatabase(
            @RequestParam("multipartFiles") MultipartFile[] files,
            @Valid UserDTO userDTO
    ) throws IOException {

        if (files == null || files.length == 0) {
            throw new RuntimeException("You must select at least one file for uploading");
        }

        User user = User
                .builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
        User savedUser = userRepository.save(user);

        StringBuilder stringBuilder = new StringBuilder(files.length);

        for (int i = 0; i < files.length; i++) {
            InputStream inputStream = files[i].getInputStream();
            AttachmentUser attachmentUser = new AttachmentUser(
                    files[i].getOriginalFilename(),
                    files[i].getSize(),
                    files[i].getContentType(),
                    savedUser
            );
            AttachmentUser savedAttachmentUser = userAttachmentRepository.save(attachmentUser);
            AttachmentContentUser attachmentContentUser = new AttachmentContentUser(
                    files[i].getBytes(),
                    savedAttachmentUser
            );
            userAttachmentContentRepository.save(attachmentContentUser);

            stringBuilder.append("File Name: ").append(files[i].getOriginalFilename()).append("\n");

            logger.info("InputStream: " + inputStream);
            logger.info("OriginalName: " + files[i].getOriginalFilename());
            logger.info("Name: " + files[i].getName());
            logger.info("ContentType: " + files[i].getContentType());
            logger.info("Size: " + files[i].getSize());

        }
        return ResponseEntity.ok(stringBuilder.toString());

    }

    @GetMapping("/{userId}")
    public HttpEntity<?> getAttachmentByUserIdWithBytes(
            @PathVariable Integer userId
    ) {
        try {
//            List<AttachmentContentUser> byAttachmentUserId =
//                    userAttachmentContentRepository.findByAttachmentUserId1(userId);
            List<AttachmentContentUser> list = userRepository.findAttachmentByUserId(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    new ApiResponse(e.getMessage(), false)
            );
        }
    }

    @GetMapping("/user/{id}")
    public HttpEntity<?> getUserAttachmentByUserIdWithBytes(
            @PathVariable Integer id
    ) {
        try {
            Optional<AttachmentUser> optionalAttachmentUser =
                    userAttachmentRepository.findByUserId(id);
            if (optionalAttachmentUser.isPresent()) {
                List<AttachmentContentUser> userList =
                        userAttachmentContentRepository.findByAttachmentUserId(optionalAttachmentUser.get());
                byte[] bytes = userList.get(id).getBytes();
                return ResponseEntity.ok(List.of(bytes));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    new ApiResponse(e.getMessage(), false)
            );
        }
    }

//    @GetMapping("/{id}")
//    public void getUserAttachmentByUserIdWithBytes(
//            @PathVariable Integer id,
//            HttpServletResponse response
//    ) throws IOException {
//        Optional<User> optionalUser = userRepository.findById(id);
//        if (optionalUser.isPresent()) {
//            Optional<AttachmentUser> optionalAttachmentUser =
//                    userAttachmentRepository.findByUserId(optionalUser.get().getId());
//            if (optionalAttachmentUser.isPresent()) {
//                Optional<AttachmentContentUser> optionalAttachmentContentUser =
//                        userAttachmentContentRepository.findByAttachmentUser_Id(optionalAttachmentUser.get());
//                if (optionalAttachmentContentUser.isPresent()) {
//                    AttachmentContentUser attachmentContentUser = optionalAttachmentContentUser.get();
//                    response.setHeader(
//                            "Content-Disposition",
//                            "attachment; file=\"" + optionalAttachmentUser.get().getFileOriginalName() + "\""
//                    );
//                    response.setContentType(optionalAttachmentUser.get().getContentType());
//                    FileCopyUtils.copy(attachmentContentUser.getBytes(), response.getOutputStream());
//
//                }
//            }
//        }
//    }

}