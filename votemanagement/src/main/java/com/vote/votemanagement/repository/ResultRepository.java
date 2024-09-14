package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result,Long> {

    Optional<Result> findByPollIdAndCandidateId(Long id, Long id1);
    //List<Result> findByPollId(Long pollId);
}
