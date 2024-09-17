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



}
