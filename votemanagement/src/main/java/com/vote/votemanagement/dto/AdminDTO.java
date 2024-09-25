package com.vote.votemanagement.dto;

import com.vote.votemanagement.enums.UserRole;
import lombok.Data;

@Data
public class AdminDTO {

    public String password;
    public String email;
    public String username;
    public UserRole role;

}
