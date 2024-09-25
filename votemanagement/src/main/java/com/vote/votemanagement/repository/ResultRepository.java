package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {
    long countByCandidateIdAndPollId(Long candidateId, Long pollId);

    // Custom method to count total votes for a specific poll
    long countByPollId(Long pollId);

    boolean existsByPollId(Long pollId);


}
