package com.vote.votemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "elections")
@Data
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin createdBy;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    private List<Poll> polls;
}
