package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public Admin createAdmin(@RequestBody Admin admin) {
        return this. adminService.creatAdmin(admin);
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public Optional<Admin> findAdminById(@PathVariable Long id) throws Exception {
        return adminService.findAdminById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteAdminById(@PathVariable Long id){
        return adminService.deleteAdminById( id);
    }

}




