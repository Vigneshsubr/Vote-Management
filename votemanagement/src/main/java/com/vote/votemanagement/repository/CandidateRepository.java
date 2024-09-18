package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    boolean existsByEmail(String email);
    Candidate findByEmail(String email);

    List<Candidate> findByPollId(Long pollId);
}
