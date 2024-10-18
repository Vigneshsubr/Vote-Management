package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.UserDto;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.enums.UserRole;
import com.vote.votemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("gender") String gender,
            @RequestParam("age") int age,
            @RequestParam("address") String address,
            @RequestParam("profile_Image") MultipartFile profileImage
    ) throws IOException {
        User user = userService.createUser(name, email, password, gender, age, address, profileImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam int age,
            @RequestParam String address,
            @RequestParam String gender,
            @RequestParam(required = false) MultipartFile profileImage) throws IOException {

        User updatedUser = userService.updateUser(id, name, email,  age, address, gender, profileImage);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/profile-image")
    public ResponseEntity<byte[]> getUserProfileImage(@PathVariable Long id) {
        byte[] profileImage = userService.getUserProfileImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG depending on your image format
                .body(profileImage);
    }
}
