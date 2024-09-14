package com.vote.votemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;


@Entity
@Table(name = "votes")
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "voter_id")
    private User voter;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date voteTime;


}
