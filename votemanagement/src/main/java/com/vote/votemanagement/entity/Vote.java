package com.vote.votemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "votes")
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;  // Add this field to map the Poll entity
}
