package com.vote.votemanagement.dto;

import lombok.Data;

@Data
public class ResultDto {
    private Long pollId;
    private String pollTitle;
    private Long candidateId;
    private String candidateName;
    private int totalVotes;
    private double votePercentage;
}
