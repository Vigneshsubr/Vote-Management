package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {


    boolean existsByCandidateAndUserAndPoll(Candidate candidate, User user, Poll poll);



    long countByCandidateIdAndPollId(Long candidateId, Long pollId);

    // Custom method to count total votes for a specific poll
    long countByPollId(Long pollId);

    boolean existsByUserIdAndPollId(Long userId, Long pollId);
}
