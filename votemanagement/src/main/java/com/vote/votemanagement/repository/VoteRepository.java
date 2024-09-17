package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;



public interface VoteRepository extends JpaRepository<Vote,Long> {

}
