package com.vote.votemanagement.dto;

import com.vote.votemanagement.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String username;
    private String email;
    private String password;
    public UserRole role;
}
