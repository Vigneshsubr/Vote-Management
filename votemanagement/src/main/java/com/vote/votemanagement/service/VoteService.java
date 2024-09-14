package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Vote;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public Vote castVote(User voter, Poll poll, Candidate candidate) throws Exception {
        // Check if the user has already voted in this poll
        Optional<Vote> existingVote = voteRepository.findByVoterAndPoll(voter, poll);
        if (existingVote.isPresent()) {
            throw new Exception("Duplicate vote not allowed. You have already voted in this poll.");
        }

        // Create and save the new vote
        Vote newVote = new Vote();
        newVote.setVoter(voter);
        newVote.setPoll(poll);
        newVote.setCandidate(candidate);
        newVote.setVoteTime(new java.util.Date());

        return voteRepository.save(newVote);
    }
}
