package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User(); // Set properties as needed
        User user2 = new User(); // Set properties as needed
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // When
        List<User> users = userController.getAllUsers();

        // Then
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById_Success() {
        // Given
        Long userId = 1L;
        User user = new User(); // Set properties as needed
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<User> response = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserById_NotFound() {
        // Given
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<User> response = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateUser() {
        // Given
        User user = new User(); // Set properties as needed
        when(userService.createUser(any(User.class))).thenReturn(user);

        // When
        User createdUser = userController.createUser(user);

        // Then
        assertEquals(user, createdUser);
    }

    @Test
    void testUpdateUser_Success() {
        // Given
        Long userId = 1L;
        User existingUser = new User(); // Set properties as needed
        User userDetails = new User(); // Set properties as needed
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(existingUser);

        // When
        ResponseEntity<User> response = userController.updateUser(userId, userDetails);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingUser, response.getBody());
    }

    @Test
    void testDeleteUser_Success() {
        // Given
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.of(new User()));

        // When
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
