package com.vote.votemanagement.dto;

import com.vote.votemanagement.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private String password;
    public UserRole role;
    public int age;
    public String address;
    public String gender;
    public byte[] profileImage; // Handle image file upload
}
