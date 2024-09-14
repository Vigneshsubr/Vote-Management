package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {


    @Query("SELECT v.candidate.id, COUNT(v.id) FROM Vote v WHERE v.poll.id = :pollId GROUP BY v.candidate.id")
    List<Object[]> countVotesByCandidate(@Param("pollId") Long pollId);

    Optional<Vote> findByVoterAndPoll(User voter, Poll poll);
}
