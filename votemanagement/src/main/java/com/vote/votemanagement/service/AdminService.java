package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    public AdminRepository adminRepository;

    public Admin creatAdmin(Admin admin) {
        return this.adminRepository.save(admin);
    }
    // Find an admin by ID
    public Optional<Admin> findAdminById(Long id) {
        return adminRepository.findById(id);
    }



    // Delete an admin by ID
    public void deleteAdmin(Long id) throws Exception {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new Exception("Admin not found");
        }
    }


    public List<Admin> getAllAdmins() {
        return this.adminRepository.findAll();
    }

    public String deleteAdminById(Long id) {
        this.adminRepository.deleteById(id);
        return "Deleted Admin Successfully";
    }
}
