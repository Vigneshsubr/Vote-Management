package com.vote.votemanagement.dto;

import lombok.Data;

@Data
public class AdminDTO {

    public String password;
    public String email;
    public String username;


    public String getName() {
        return username;
    }
    public void setName(String name) {
        this.username = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
