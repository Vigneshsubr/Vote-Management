package com.vote.votemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;


    @ManyToOne
    private Election election;

    // Constructors
    public Poll() {     }

    public Poll(String question, Election election) {
        this.question = question;
        this.election = election;
    }



}
