package com.vote.votemanagement.service;

import com.vote.votemanagement.dto.VoteDTO;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.entity.Vote;
import com.vote.votemanagement.exception.InvalidVoteException;
import com.vote.votemanagement.repository.CandidateRepository;
import com.vote.votemanagement.repository.PollRepository;
import com.vote.votemanagement.repository.UserRepository;
import com.vote.votemanagement.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    public VoteDTO createVote(VoteDTO voteDTO) {
        // Ensure Candidate, User, and Poll exist
        Candidate candidate = candidateRepository.findById(voteDTO.getCandidateId())
                .orElseThrow(() -> new InvalidVoteException("Candidate not found"));
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new InvalidVoteException("User not found"));
        Poll poll = pollRepository.findById(voteDTO.getPollId())
                .orElseThrow(() -> new InvalidVoteException("Poll not found"));

        // Check for existing vote
        if (voteRepository.existsByCandidateAndUserAndPoll(candidate, user, poll)) {
            throw new InvalidVoteException("Duplicate vote is not allowed");
        }

        // Create and save vote
        Vote vote = new Vote();
        vote.setCandidate(candidate);
        vote.setUser(user);
        vote.setPoll(poll);

        return convertToDTO(voteRepository.save(vote));
    }

    public VoteDTO getVoteById(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new InvalidVoteException("Vote not found"));
        return convertToDTO(vote);
    }

    public void deleteVote(Long id) {
        if (!voteRepository.existsById(id)) {
            throw new InvalidVoteException("Vote not found");
        }
        voteRepository.deleteById(id);
    }

    private VoteDTO convertToDTO(Vote vote) {
        // Convert Vote entity to VoteDTO
        // Implement your DTO conversion logic here
        return new VoteDTO(vote.getId(), vote.getCandidate().getId(), vote.getUser().getId(), vote.getPoll().getId());
    }
}
