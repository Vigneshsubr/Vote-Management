package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll,Long> {
    List<Poll> findByElectionId(Long electionId);
}
