package com.vote.votemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    private Long id;           // Added this field for Vote ID
    private Long candidateId;
    private Long userId;       // Added this field for User ID
    private Long pollId;
    private Long electionId;
}
