package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}

