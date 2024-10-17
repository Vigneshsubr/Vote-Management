package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.CandidateDTO;
import com.vote.votemanagement.dto.ResponseDTO;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.exception.InvalidJwtException;
import com.vote.votemanagement.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    // Get all candidates
    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    // Get candidate by ID
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.getCandidateById(id);
        return candidate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    // Create a new candidate
//    @PostMapping
//    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidateRequest, @RequestParam Long pollId) {
//        Candidate createdCandidate = candidateService.createCandidate(candidateRequest, pollId);
//        return ResponseEntity.ok(createdCandidate);
//    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody CandidateDTO candidateDTO) {
        Candidate createdCandidate = candidateService.createCandidate(candidateDTO);
        return ResponseEntity.ok(createdCandidate);
    }

    // Delete candidate by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateCandidate(
            @PathVariable Long id,
            @RequestBody CandidateDTO candidateDTO) throws InvalidJwtException {

        // Call the service method to update the candidate
        return candidateService.updateCandidate(id, candidateDTO);
    }
}
