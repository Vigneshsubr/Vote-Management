package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.repository.PollRepository;
import com.vote.votemanagement.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private ElectionRepository electionRepository;

    // Retrieve all polls
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    // Retrieve poll by ID
    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    // Create a new poll
    public Poll createPoll(Poll poll, Long electionId) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Election not found with ID: " + electionId));

        // Set the election to the poll
        poll.setElection(election);

        // Save the poll
        return pollRepository.save(poll);
    }

    // Delete poll by ID
    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}
