package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Vote;
import com.vote.votemanagement.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public Optional<Vote> getVoteById(Long id) {
        return voteRepository.findById(id);
    }

    public Vote createVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public Vote updateVote(Long id, Vote voteDetails) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vote not found"));
        vote.setUser(voteDetails.getUser());
        vote.setCandidate(voteDetails.getCandidate());
        return voteRepository.save(vote);
    }

    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }
}
