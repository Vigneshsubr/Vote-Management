package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.exception.UserNotFoundException;
import com.vote.votemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User(); // Set properties as needed
        User user2 = new User(); // Set properties as needed
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() {
        // Given
        Long userId = 1L;
        User user = new User(); // Set properties as needed
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.getUserById(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

//    @Test
//    void testGetUserById_NotFound() {
//        // Given
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // When
//        Optional<User> result = userService.getUserById(userId);
//
//        // Then
//        assertFalse(result.isPresent());
//        verify(userRepository, times(1)).findById(userId);
//    }

    @Test
    void testCreateUser() {
        // Given
        User user = new User(); // Set properties as needed
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User createdUser = userService.createUser(user);

        // Then
        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_Success() {
        // Given
        Long userId = 1L;
        User existingUser = new User(); // Set properties as needed
        User userDetails = new User(); // Set properties as needed
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // When
        User updatedUser = userService.updateUser(userId, userDetails);

        // Then
        assertEquals(existingUser, updatedUser);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

//    @Test
//    void testUpdateUser_NotFound() {
//        // Given
//        Long userId = 1L;
//        User userDetails = new User(); // Set properties as needed
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // When & Then
//        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
//            userService.updateUser(userId, userDetails);
//        });
//        assertEquals("User not found", thrown.getMessage());
//        verify(userRepository, times(1)).findById(userId);
//        verify(userRepository, never()).save(any(User.class));
//    }

    @Test
    void testDeleteUser_Success() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User())); // Ensure user exists

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

//    @Test
//    void testDeleteUser_NotFound() {
//        // Given
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // When & Then
//        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
//            userService.deleteUser(userId);
//        });
//        assertEquals("User not found", thrown.getMessage());
//        verify(userRepository, never()).deleteById(userId);
//    }
}

