package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.repository.CandidateRepository;
import com.vote.votemanagement.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PollRepository pollRepository;

    // Get all candidates
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // Get candidate by ID
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    // Create a new candidate
    public Candidate createCandidate(Candidate candidate, Long pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found with ID: " + pollId));

        // Set the poll for the candidate
        candidate.setPoll(poll);

        // Save the candidate
        return candidateRepository.save(candidate);
    }

    // Delete a candidate by ID
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}
