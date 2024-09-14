package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    public Optional<Poll> findPollById(Long id) {
        return pollRepository.findById(id);
    }

    public Poll savePoll(Poll poll) {
        return pollRepository.save(poll);
    }
}
