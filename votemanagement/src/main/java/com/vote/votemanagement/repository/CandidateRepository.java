package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
