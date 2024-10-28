package com.vote.votemanagement.dto;

import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.enums.UserRole;
import lombok.Data;

@Data
public class CandidateDTO {
    public String password;
    public String email;
    public String name;
    public UserRole role;
    public String gender;
    public String address;
    public int age;
    public Long pollId;
    public byte[] profileImage;

}