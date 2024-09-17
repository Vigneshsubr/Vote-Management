package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.repository.ElectionRepository;
import com.vote.votemanagement.repository.PollRepository;
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

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    public Poll createPoll(Poll poll, Long electionId) {
        // Fetch the election by ID
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Election not found for id: " + electionId));

        // Set the election for this poll
        poll.setElection(election);

        // Save the poll
        return pollRepository.save(poll);
    }


    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}
