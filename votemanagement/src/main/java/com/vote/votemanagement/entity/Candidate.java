package com.vote.votemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "candidates")
@Data
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String campaignDetails;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;
}
