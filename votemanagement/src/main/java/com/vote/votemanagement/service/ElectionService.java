package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.exception.ElectionNotFoundException;
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
        Optional<Election> election = electionRepository.findById(id);
        if (election.isEmpty()) {
            throw new ElectionNotFoundException("Election with ID " + id + " not found");
        }
        return election;
    }

    // Create a new election
    public Election createElection(Election election) {
        return electionRepository.save(election);
    }

    // Delete election by ID
    public void deleteElection(Long id) {
        Election election = electionRepository.findById(id)
                .orElseThrow(() -> new ElectionNotFoundException("Election with ID " + id + " not found"));

        electionRepository.deleteById(id);
    }
}
