package com.vote.votemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "results")
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private int totalVotes;

    private boolean isWinner;

    // Getters and Setters
}
