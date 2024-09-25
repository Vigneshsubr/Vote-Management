package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.exception.AdminNotFoundException;
import com.vote.votemanagement.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAdmins() {
        // Given
        Admin admin1 = new Admin(); // Set properties as needed
        Admin admin2 = new Admin(); // Set properties as needed
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin1, admin2));

        // When
        List<Admin> admins = adminService.getAllAdmins();

        // Then
        assertEquals(2, admins.size());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void testGetAdminById_Success() {
        // Given
        Long adminId = 1L;
        Admin admin = new Admin(); // Set properties as needed
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        // When
        Optional<Admin> result = adminService.getAdminById(adminId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(admin, result.get());
        verify(adminRepository, times(1)).findById(adminId);
    }

    @Test
    void testGetAdminById_NotFound() {
        // Given
        Long adminId = 1L;
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // When & Then
        AdminNotFoundException thrown = assertThrows(AdminNotFoundException.class, () -> {
            adminService.getAdminById(adminId);
        });
        assertEquals("Admin with ID " + adminId + " not found", thrown.getMessage());
        verify(adminRepository, times(1)).findById(adminId);
    }

    @Test
    void testCreateAdmin() {
        // Given
        Admin admin = new Admin(); // Set properties as needed
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        // When
        Admin createdAdmin = adminService.createAdmin(admin);

        // Then
        assertEquals(admin, createdAdmin);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void testUpdateAdmin_Success() {
        // Given
        Long adminId = 1L;
        Admin existingAdmin = new Admin(); // Set properties as needed
        Admin adminDetails = new Admin(); // Set properties as needed
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(existingAdmin));
        when(adminRepository.save(any(Admin.class))).thenReturn(existingAdmin);

        // When
        Admin updatedAdmin = adminService.updateAdmin(adminId, adminDetails);

        // Then
        assertEquals(existingAdmin, updatedAdmin);
        verify(adminRepository, times(1)).findById(adminId);
        verify(adminRepository, times(1)).save(existingAdmin);
    }

    @Test
    void testUpdateAdmin_NotFound() {
        // Given
        Long adminId = 1L;
        Admin adminDetails = new Admin(); // Set properties as needed
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // When & Then
        AdminNotFoundException thrown = assertThrows(AdminNotFoundException.class, () -> {
            adminService.updateAdmin(adminId, adminDetails);
        });
        assertEquals("Admin with ID " + adminId + " not found", thrown.getMessage());
        verify(adminRepository, times(1)).findById(adminId);
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    void testDeleteAdmin_Success() {
        // Given
        Long adminId = 1L;
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(new Admin())); // Ensure admin exists

        // When
        adminService.deleteAdmin(adminId);

        // Then
        verify(adminRepository, times(1)).deleteById(adminId);
    }

    @Test
    void testDeleteAdmin_NotFound() {
        // Given
        Long adminId = 1L;
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // When & Then
        AdminNotFoundException thrown = assertThrows(AdminNotFoundException.class, () -> {
            adminService.deleteAdmin(adminId);
        });
        assertEquals("Admin with ID " + adminId + " not found", thrown.getMessage());
        verify(adminRepository, never()).deleteById(adminId);
    }
}
