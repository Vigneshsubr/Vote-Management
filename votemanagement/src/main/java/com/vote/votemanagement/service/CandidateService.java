package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Optional<Candidate> findCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public List<Candidate> findAllCandidates() {
        return  candidateRepository.findAll();
    }
}
