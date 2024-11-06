package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.CandidateDTO;
import com.vote.votemanagement.dto.ResponseDTO;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.enums.UserRole;
import com.vote.votemanagement.exception.InvalidJwtException;
import com.vote.votemanagement.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    @PostMapping
    public ResponseEntity<Candidate> createCandidate(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("gender") String gender,
            @RequestParam("age") int age,
            @RequestParam("address") String address,
            //@RequestParam("role") UserRole role,
            @RequestParam("pollId") Long pollId,
            @RequestParam("profile_Image") MultipartFile profileImage
    ) throws IOException {
        // Use the candidateService to create the candidate
        Candidate candidate = candidateService.createCandidate(name, email, password, gender, age, address,  pollId, profileImage);

        // Return the created candidate with a 201 status
        return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
    }

    // Delete candidate by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String gender,
            @RequestParam int age,
            @RequestParam String address,
            @RequestParam(required = false) Long pollId,
            @RequestParam(required = false) MultipartFile profileImage) throws IOException {

        // Update candidate with the service method
        Candidate updatedCandidate = candidateService.updateCandidate(id, name, email, password, gender, age, address, pollId, profileImage);
        return ResponseEntity.ok(updatedCandidate);
    }



}
