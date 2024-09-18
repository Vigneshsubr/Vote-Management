package com.vote.votemanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private Long id;
    private Long candidateId;
    private Long userId;
    private Long pollId;
}
