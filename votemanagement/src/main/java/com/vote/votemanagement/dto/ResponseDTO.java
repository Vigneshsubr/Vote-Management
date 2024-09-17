package com.vote.votemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDTO {

    public ResponseDTO() {
        // TODO Auto-generated constructor stub
    }
    private String message;
    private Object data;
    private Integer statusCode;



}
