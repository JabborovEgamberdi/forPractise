package com.example.forAttachment.controller;

import com.example.forAttachment.entity.attachment.Attachment;
import com.example.forAttachment.entity.attachment.AttachmentContent;
import com.example.forAttachment.entity.user.User;
import com.example.forAttachment.payload.UserDTO;
import com.example.forAttachment.repository.attachment.AttachmentContentRepository;
import com.example.forAttachment.repository.attachment.AttachmentRepository;
import com.example.forAttachment.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

//    @PostMapping("/upload")
//    public String uploadFile(MultipartHttpServletRequest request) throws IOException {
//        Iterator<String> fileNames = request.getFileNames();
//        MultipartFile file = request.getFile(fileNames.next()); // next() method instead of getById()
//        if (file != null) {
//            Attachment attachment = new Attachment(
//                    file.getOriginalFilename(),
//                    file.getSize(),
//                    file.getContentType()
//            );
//            Attachment savedAttachment = attachmentRepository.save(attachment);
//            AttachmentContent attachmentContent = new AttachmentContent(
//                    file.getBytes(),
//                    savedAttachment
//            );
//            attachmentContentRepository.save(attachmentContent);
//            return "File saved. Id : " + attachment.getId();
//        }
//        return "Error";
//    }

    @GetMapping("/getFile/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();

            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());

            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setHeader("Content-Disposition", "attachment; file=\"" + attachment.getFileOriginalName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
        }
    }


    @PostMapping("/uploadWithUser")
    public String uploadFileWithUser(MultipartHttpServletRequest request, UserDTO userDTO) throws IOException {

        User user = new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getUsername(),
                userDTO.getPassword()
        );
        User savedUser = userRepository.save(user);


        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next()); // next() method instead of getById()
        if (file != null) {
            Attachment attachment = new Attachment(
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType(),
                    savedUser
            );
            Attachment savedAttachment = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent = new AttachmentContent(
                    file.getBytes(),
                    savedAttachment
            );
            attachmentContentRepository.save(attachmentContent);

//            savedUser.setAttachment(attachment, attachment);

            return "File saved. Id : " + attachment.getId();

        }
        return "Error";
    }

    @GetMapping("/getFileWithUser/{id}")
    public void getFileWithUser(@PathVariable Integer id, HttpServletResponse response) throws IOException {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<Attachment> byUserId = attachmentRepository.findByUserId(user.getId());

            if (byUserId.isPresent()) {
                Attachment attachment = byUserId.get();

                Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(byUserId.get().getId());

                if (optionalAttachmentContent.isPresent()) {
                    AttachmentContent attachmentContent = optionalAttachmentContent.get();

                    response.setHeader("Content-Disposition", "attachment; file=\"" + attachment.getFileOriginalName() + "\"");
                    response.setContentType(attachment.getContentType());
                    FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());

                }
            }

        }

    }


    @GetMapping("/getFileWithUserID/{id}")
    public @ResponseBody ResponseEntity getFileWithUserID(@RequestParam(value = "id", required = false) Integer id, HttpServletResponse response) throws IOException {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<Attachment> byUserId = attachmentRepository.findByUserId(user.getId());

//            attachmentContentRepository.findByAttachmentId(byUserId.get().getId());

            if (byUserId.isPresent()) {
                Attachment attachment = byUserId.get();

                Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(byUserId.get().getId());

                if (optionalAttachmentContent.isPresent()) {
                    AttachmentContent attachmentContent = optionalAttachmentContent.get();

                    response.setHeader("Content-Disposition", "attachment; file=\"" + attachment.getFileOriginalName() + "\"");
                    response.setContentType(attachment.getContentType());
//                    response.addHeader(user.getFirstName(), user.getLastName());
                    FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());

//                    FileCopyUtils.copy(user.getFirstName(), response.getWriter());
//                    FileCopyUtils.copy(user.getFirstName(), response.getWriter());

                    response.flushBuffer();

//                    return ResponseEntity<>(
//                            user.getFirstName(),
//                            user.getLastName(),
//                            HttpStatus.OK;
//                    );
                }
            }

        }

        return new ResponseEntity<>("There is no file with this id", HttpStatus.NO_CONTENT);
    }

}
