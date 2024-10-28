package com.vote.votemanagement.service;

import com.vote.votemanagement.dto.VoteRequest;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.entity.Vote;
import com.vote.votemanagement.exception.CandidateNotFoundException;
import com.vote.votemanagement.exception.InvalidVoteException;
import com.vote.votemanagement.exception.VoteAlreadyExistsException;
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
    public boolean castVote(Long userId, Long pollId, Long candidateId) {
        // Check if the user has already voted in the poll
        if (voteRepository.existsByUserIdAndPollId(userId, pollId)) {
            throw new VoteAlreadyExistsException("User has already voted in this poll.");
        }

        // Retrieve entities and check existence
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidVoteException("User not found with ID: " + userId));
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new InvalidVoteException("Poll not found with ID: " + pollId));
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + candidateId));

        // Create and save the new vote
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setPoll(poll);
        vote.setCandidate(candidate);

        voteRepository.save(vote);
        return true;
    }


    // Retrieve vote by ID
    public VoteRequest getVoteById(Long id) {
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
    private VoteRequest convertToDTO(Vote vote) {
        return new VoteRequest(vote.getId(), vote.getCandidate().getId(), vote.getUser().getId(), vote.getPoll().getId());
    }
}
