package com.vote.votemanagement.dto;

import com.vote.votemanagement.enums.UserRole;
import lombok.Data;

@Data
public class AdminDTO {

    public String password;
    public String email;
    public String name;
    public String gender;
    public String address;
    public int age;
    public UserRole role;

}
