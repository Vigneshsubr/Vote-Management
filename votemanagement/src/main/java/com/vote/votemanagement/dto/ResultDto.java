package com.vote.votemanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ResultDto {
    private Long pollId;
    private String pollTitle;
    private Long candidateId;
    private String candidateName;
    private int totalVotes;
    private double votePercentage;
    private LocalDateTime calculatedAt;
}
