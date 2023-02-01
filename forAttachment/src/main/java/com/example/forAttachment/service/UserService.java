package com.example.forAttachment.service;

import com.example.forAttachment.entity.user.AttachmentContentUser;
import com.example.forAttachment.entity.user.AttachmentUser;
import com.example.forAttachment.entity.user.User;
import com.example.forAttachment.payload.ApiResponse;
import com.example.forAttachment.payload.UserDTO;
import com.example.forAttachment.repository.attachment.AttachmentContentRepository;
import com.example.forAttachment.repository.user.UserAttachmentContentRepository;
import com.example.forAttachment.repository.user.UserAttachmentRepository;
import com.example.forAttachment.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAttachmentRepository userAttachmentRepository;
    private final UserAttachmentContentRepository userAttachmentContentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public UserService(
            UserRepository userRepository,
            UserAttachmentRepository userAttachmentRepository,
            UserAttachmentContentRepository userAttachmentContentRepository,
            AttachmentContentRepository attachmentContentRepository
    ) {
        this.userRepository = userRepository;
        this.userAttachmentRepository = userAttachmentRepository;
        this.userAttachmentContentRepository = userAttachmentContentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public ApiResponse getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("There is no user with this id", false);
    }


//    public ApiResponse getByIdUser(Integer id, HttpServletResponse response) {
//        Optional<User> optionalUser = userRepository.findById(id);
//        Optional<AttachmentUser> byUserId = userAttachmentRepository.findByUserId(optionalUser.get().getId());
//        Optional<AttachmentContentUser> byAttachmentUser_id =
//                userAttachmentContentRepository.findByAttachmentUser_Id(byUserId.get().getUser().getAttachmentUser());
//
//        if (byAttachmentUser_id.isPresent()) {
//            response.setHeader("Content-Disposition", "attachment; file=\"" + byUserId.get().getFileOriginalName() + "\"");
//            response.addHeader(optionalUser.get().getFirstName(), optionalUser.get().getLastName());
//            response.setContentType(byUserId.get().getContentType());
//            try {
//                FileCopyUtils.copy(byAttachmentUser_id.get().getBytes(), response.getOutputStream());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return new ApiResponse("Success", true);
//        }
//
//        return new ApiResponse("There is no user with this id", false);
//    }

    public ApiResponse addUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);

        AttachmentUser attachmentUser = new AttachmentUser();
        attachmentUser.setUser(user);
        attachmentUser.setFileOriginalName(userDTO.getFile().getOriginalFilename());
        attachmentUser.setContentType(userDTO.getFile().getContentType());
        attachmentUser.setSize(userDTO.getFile().getSize());
        userAttachmentRepository.save(attachmentUser);

        AttachmentContentUser attachmentContentUser = new AttachmentContentUser();
        attachmentContentUser.setAttachmentUser(attachmentUser);
        try {
            attachmentContentUser.setBytes(userDTO.getFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userAttachmentContentRepository.save(attachmentContentUser);

        user.addAttachment(attachmentUser);
        userRepository.save(user);

        return new ApiResponse("new user added successfully", true);
    }

    public ApiResponse updateUser(Integer id, UserDTO userDTO) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);

            Optional<AttachmentUser> optionalAttachmentUser = userAttachmentRepository.findById(id);
            if (optionalAttachmentUser.isPresent()) {
                AttachmentUser attachmentUser = optionalAttachmentUser.get();
                attachmentUser.setUser(user);
                attachmentUser.setFileOriginalName(userDTO.getFile().getOriginalFilename());
                attachmentUser.setContentType(userDTO.getFile().getContentType());
                attachmentUser.setSize(userDTO.getFile().getSize());
                userAttachmentRepository.save(attachmentUser);

                Optional<AttachmentContentUser> optionalAttachmentContentUser = userAttachmentContentRepository.findById(id);

                AttachmentContentUser attachmentContentUser = optionalAttachmentContentUser.get();
                attachmentContentUser.setAttachmentUser(attachmentUser);
                try {
                    attachmentContentUser.setBytes(userDTO.getFile().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                userAttachmentContentRepository.save(attachmentContentUser);
            }

            return new ApiResponse("User update successfully", true);

        }

        return new ApiResponse("there is no user with this id", false);
    }

    public ApiResponse deleteUser(Integer id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return new ApiResponse("User deleted successfully", true);
        }

        return new ApiResponse("There is no user with this id", false);
    }

    public AttachmentContentUser getBytes(Integer id){
        return userAttachmentContentRepository.findById(id)
                .orElseThrow();
    }

}
