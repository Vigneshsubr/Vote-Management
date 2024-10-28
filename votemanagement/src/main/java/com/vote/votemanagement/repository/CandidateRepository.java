package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    boolean existsByEmail(String email);
    Candidate findByEmail(String email);

    List<Candidate> findByPollId(Long pollId);

    boolean existsByIdAndPollId(Long candidateId, Long pollId);
}

