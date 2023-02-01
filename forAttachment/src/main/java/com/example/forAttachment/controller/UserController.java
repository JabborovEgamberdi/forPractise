package com.example.forAttachment.controller;

import com.example.forAttachment.entity.user.AttachmentContentUser;
import com.example.forAttachment.entity.user.User;
import com.example.forAttachment.payload.ApiResponse;
import com.example.forAttachment.payload.UserDTO;
import com.example.forAttachment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    final UserService userService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<User> usersList = userService.getAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/getByID/{id}")
    public HttpEntity<?> getByID(Integer id) {
        ApiResponse deleteUser = userService.getById(id);
        return new ResponseEntity<>(deleteUser, HttpStatus.OK);
    }

//    @GetMapping("/getByUserID/{id}")
//    public HttpEntity<?> getByUserID(@PathVariable Integer id, HttpServletResponse response) {
//        ApiResponse apiResponse = userService.getByIdUser(id, response);
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }

    //    @EventListener(ApplicationReadyEvent.class)
    @PostMapping("/add")
    public HttpEntity<?> addUser(@ModelAttribute UserDTO userDTO) {
        ApiResponse addUser = userService.addUser(userDTO);
        return new ResponseEntity<>(addUser, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        ApiResponse updateUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteUser(Integer id) {
        ApiResponse deleteUser = userService.deleteUser(id);
        return new ResponseEntity<>(deleteUser, HttpStatus.OK);
    }

    @GetMapping("/bytes/{id}")
    public HttpEntity<?> getBytes(@PathVariable Integer id) {
        try {
            AttachmentContentUser bytes = userService.getBytes(id);
            return ResponseEntity.ok().body(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    new ApiResponse(e.getMessage(), false)
            );
        }
    }


}
