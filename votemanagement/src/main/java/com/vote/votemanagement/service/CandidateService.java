package com.vote.votemanagement.service;

import com.vote.votemanagement.dto.CandidateDTO;
import com.vote.votemanagement.dto.ResponseDTO;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.exception.CandidateNotFoundException;
import com.vote.votemanagement.exception.InvalidJwtException;
import com.vote.votemanagement.exception.PollNotFoundException;
import com.vote.votemanagement.repository.CandidateRepository;
import com.vote.votemanagement.repository.PollRepository;
import com.vote.votemanagement.util.Constants;
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
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isEmpty()) {
            throw new CandidateNotFoundException("Candidate with ID " + id + " not found");
        }
        return candidate;
    }

//    // Create a new candidate
//    public Candidate createCandidate(Candidate candidate, Long pollId) {
//        Poll poll = pollRepository.findById(pollId)
//                .orElseThrow(() -> new PollNotFoundException("Poll not found with ID: " + pollId));
//
//        // Set the poll for the candidate
//        candidate.setPoll(poll);
//
//        // Save the candidate
//        return candidateRepository.save(candidate);
//    }

    // CandidateService.java

    public Candidate createCandidate(CandidateDTO candidateDTO) {
        Poll poll = pollRepository.findById(candidateDTO.getPollId())
                .orElseThrow(() -> new PollNotFoundException("Poll not found with ID: " + candidateDTO.getPollId()));

        // Create a new Candidate entity from CandidateDTO
        Candidate candidate = new Candidate();
        candidate.setName(candidateDTO.getName());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setPassword(candidateDTO.getPassword()); // Ensure password is hashed before saving
        candidate.setGender(candidateDTO.getGender());
        candidate.setAge(candidateDTO.getAge());
        candidate.setAddress(candidateDTO.getAddress());
        candidate.setRole(candidateDTO.getRole());
        candidate.setPoll(poll);

        // Save the candidate
        return candidateRepository.save(candidate);
    }


    // Delete a candidate by ID
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with ID " + id + " not found"));

        candidateRepository.deleteById(id);
    }

    public ResponseDTO updateCandidate(Long candidateId, CandidateDTO candidateDTO) throws InvalidJwtException {

        try {
            // Fetch the candidate by ID
            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new InvalidJwtException("Candidate not found"));

            // Update candidate details if provided (e.g., email, username, etc.)
            candidate.setName(candidateDTO.getName());
            candidate.setGender(candidateDTO.getGender());

            // Check if pollId is provided for updating the poll
            if (candidateDTO.getPollId() != null) {
                Poll poll = pollRepository.findById(candidateDTO.getPollId())
                        .orElseThrow(() -> new PollNotFoundException("Poll not found with this id"));
                candidate.setPoll(poll); // Associate poll with candidate
            }

            // Save the updated candidate
            return ResponseDTO.builder()
                    .message(Constants.UPDATED)
                    .data(candidateRepository.save(candidate))
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseDTO.builder()
                    .message("An unexpected error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
    }

}
