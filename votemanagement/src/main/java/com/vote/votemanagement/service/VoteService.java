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
import jakarta.transaction.Transactional;
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

    // Create a new vote
    @Transactional
    public VoteDTO createVote(VoteDTO voteDTO) {
        // Ensure Candidate, User, and Poll exist
        Candidate candidate = candidateRepository.findById(voteDTO.getCandidateId())
                .orElseThrow(() -> new InvalidVoteException("Candidate not found with ID: " + voteDTO.getCandidateId()));
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new InvalidVoteException("User not found with ID: " + voteDTO.getUserId()));
        Poll poll = pollRepository.findById(voteDTO.getPollId())
                .orElseThrow(() -> new InvalidVoteException("Poll not found with ID: " + voteDTO.getPollId()));

        // Check if a vote already exists for this user in this poll for the given candidate
        if (voteRepository.existsByCandidateAndUserAndPoll(candidate, user, poll)) {
            throw new InvalidVoteException("Duplicate vote is not allowed for user ID: " + voteDTO.getUserId());
        }

        // Create and save vote
        Vote vote = new Vote();
        vote.setCandidate(candidate);
        vote.setUser(user);
        vote.setPoll(poll);

        System.out.println("Vote before saving: " + vote); // Debugging

        return convertToDTO(voteRepository.save(vote));
    }

    // Retrieve vote by ID
    public VoteDTO getVoteById(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new InvalidVoteException("Vote not found with ID: " + id));
        return convertToDTO(vote);
    }

    // Delete vote by ID
    public void deleteVote(Long id) {
        if (!voteRepository.existsById(id)) {
            throw new InvalidVoteException("Vote not found with ID: " + id);
        }
        voteRepository.deleteById(id);
    }

    // Convert Vote entity to VoteDTO
    private VoteDTO convertToDTO(Vote vote) {
        return new VoteDTO(vote.getId(), vote.getCandidate().getId(), vote.getUser().getId(), vote.getPoll().getId());
    }
}
