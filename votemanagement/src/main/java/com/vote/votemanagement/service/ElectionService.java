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

    // Create a new election
    public Election createElection(Election election) {
        return electionRepository.save(election);
    }

    // Find an election by its ID
    public Optional<Election> findElectionById(Long id) {
        return electionRepository.findById(id);
    }

    // Get all elections
    public List<Election> findAllElections() {
        return electionRepository.findAll();
    }

    // Update an election
    public Election updateElection(Long id, Election updatedElection) throws Exception {
        Optional<Election> existingElection = electionRepository.findById(id);
        if (existingElection.isPresent()) {
            Election election = existingElection.get();
            election.setTitle(updatedElection.getTitle());
            election.setStartDate(updatedElection.getStartDate());
            election.setEndDate(updatedElection.getEndDate());
            return electionRepository.save(election);
        } else {
            throw new Exception("Election not found");
        }
    }

    // Delete an election by its ID
    public void deleteElection(Long id) throws Exception {
        if (electionRepository.existsById(id)) {
            electionRepository.deleteById(id);
        } else {
            throw new Exception("Election not found");
        }
    }
}
