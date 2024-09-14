package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll,Long> {
}
