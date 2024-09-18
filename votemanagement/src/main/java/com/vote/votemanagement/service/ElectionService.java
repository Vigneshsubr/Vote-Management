package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    // Retrieve all elections
    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    // Retrieve election by ID
    public Optional<Election> getElectionById(Long id) {
        return electionRepository.findById(id);
    }

    // Create a new election
    public Election createElection(Election election) {
        return electionRepository.save(election);
    }

    // Delete election by ID
    public void deleteElection(Long id) {
        electionRepository.deleteById(id);
    }
}
