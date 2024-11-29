package com.vote.votemanagement.dto;

import lombok.Data;

@Data
public class SignoutResponseDto {
    public String message;

    public SignoutResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
