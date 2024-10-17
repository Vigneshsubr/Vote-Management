package com.vote.votemanagement.service;

import com.vote.votemanagement.dto.UserDto;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.enums.UserRole;
import com.vote.votemanagement.exception.UserNotFoundException;
import com.vote.votemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        return user;
    }

    public User createUser(String name, String email, String password, String gender, int age, String address, MultipartFile profileImage) throws IOException {
        System.out.println(email);
        System.out.println(name);
        System.out.println(Arrays.toString(profileImage.getBytes()));
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(UserRole.VOTER);
        user.setGender(gender);
        user.setAge(age);
        user.setAddress(address);
        user.setProfileImage(profileImage.getBytes());
        user.setPassword(passwordEncoder.encode(password));

//        // Convert image to byte[] and set in user entity
//        if (profileImage != null && !profileImage.isEmpty()) {
//            user.setProfileImage(profileImage.getBytes());
//        }

        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setAge(userDetails.getAge());
        user.setGender(userDetails.getGender());

        // Update profile image if provided
        if (userDetails.getProfileImage() != null) {
            user.setProfileImage(userDetails.getProfileImage());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        userRepository.deleteById(id);
    }

    public byte[] getUserProfileImage(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        return user.getProfileImage();
    }
}
