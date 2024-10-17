//package com.vote.votemanagement.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.vote.votemanagement.config.TokenProvider;
//import com.vote.votemanagement.entity.Admin;
//import com.vote.votemanagement.service.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AdminController.class)
//public class AdminControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TokenProvider tokenProvider;
//
//    @Mock
//    private AdminService adminService;
//
//    @InjectMocks
//    private AdminController adminController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllAdmins() throws Exception {
//        // Given
//        Admin admin1 = new Admin(); // Set properties as needed
//        Admin admin2 = new Admin(); // Set properties as needed
//        when(adminService.getAllAdmins()).thenReturn(Arrays.asList(admin1, admin2));
//
//        // When & Then
//        mockMvc.perform(get("/api/v1/admins")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0]").isNotEmpty())
//                .andExpect(jsonPath("$[1]").isNotEmpty());
//    }
//
//    @Test
//    void testGetAdminById_Success() throws Exception {
//        // Given
//        Long adminId = 1L;
//        Admin admin = new Admin(); // Set properties as needed
//        when(adminService.getAdminById(adminId)).thenReturn(Optional.of(admin));
//
//        // When & Then
//        mockMvc.perform(get("/api/v1/admins/{id}", adminId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(adminId));
//    }
//
//    @Test
//    void testGetAdminById_NotFound() throws Exception {
//        // Given
//        Long adminId = 1L;
//        when(adminService.getAdminById(adminId)).thenReturn(Optional.empty());
//
//        // When & Then
//        mockMvc.perform(get("/api/v1/admins/{id}", adminId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testCreateAdmin() throws Exception {
//        // Given
//        Admin admin = new Admin(); // Set properties as needed
//        when(adminService.createAdmin(any(Admin.class))).thenReturn(admin);
//
//        // When & Then
//        mockMvc.perform(post("/api/v1/admins")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(admin)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(admin.getId()));
//    }
//
//    @Test
//    void testUpdateAdmin_Success() throws Exception {
//        // Given
//        Long adminId = 1L;
//        Admin adminDetails = new Admin(); // Set properties as needed
//        when(adminService.updateAdmin(anyLong(), any(Admin.class))).thenReturn(adminDetails);
//
//        // When & Then
//        mockMvc.perform(put("/api/v1/admins/{id}", adminId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDetails)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(adminId));
//    }
//
//    @Test
//    void testUpdateAdmin_NotFound() throws Exception {
//        // Given
//        Long adminId = 1L;
//        Admin adminDetails = new Admin(); // Set properties as needed
//        when(adminService.updateAdmin(anyLong(), any(Admin.class)))
//                .thenThrow(new RuntimeException("Admin not found"));
//
//        // When & Then
//        mockMvc.perform(put("/api/v1/admins/{id}", adminId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDetails)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testDeleteAdmin_Success() throws Exception {
//        // Given
//        Long adminId = 1L;
//        doNothing().when(adminService).deleteAdmin(adminId);
//
//        // When & Then
//        mockMvc.perform(delete("/api/v1/admins/{id}", adminId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void testDeleteAdmin_NotFound() throws Exception {
//        // Given
//        Long adminId = 1L;
//        doThrow(new RuntimeException("Admin not found")).when(adminService).deleteAdmin(adminId);
//
//        // When & Then
//        mockMvc.perform(delete("/api/v1/admins/{id}", adminId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//}
