package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateService.saveCandidate(candidate);
    }

    @GetMapping("/{id}")
    public Optional<Candidate> getCandidateById(@PathVariable Long id) {
        return candidateService.findCandidateById(id);
    }

    @GetMapping
    public List<Candidate>  getAllCandidates(){
        return this.candidateService.findAllCandidates();
    }

    @PutMapping("/{id}")
    public Candidate updateCandidate(@PathVariable Long id, @RequestBody Candidate updatedCandidate) throws Exception {
        return candidateService.saveCandidate(updatedCandidate);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidate(@PathVariable Long id) throws Exception {
        candidateService.findCandidateById(id)
                .orElseThrow(() -> new Exception("Candidate not found"));
    }
}

