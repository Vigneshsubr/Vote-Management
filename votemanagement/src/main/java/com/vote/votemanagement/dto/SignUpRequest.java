package com.vote.votemanagement.dto;

import com.vote.votemanagement.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private String gender;
    private String address;
    private int age;
}

