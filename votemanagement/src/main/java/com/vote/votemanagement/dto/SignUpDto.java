package com.vote.votemanagement.dto;


import com.vote.votemanagement.enums.UserRole;
import lombok.Data;


@Data
public class SignUpDto {

    private String email;
    private String password;
    private UserRole role;

}
