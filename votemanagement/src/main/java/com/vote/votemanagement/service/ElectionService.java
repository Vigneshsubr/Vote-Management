package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    // Create a new election
    public Election createElection(Election election) {
        // Handle the relationships correctly
        return electionRepository.save(election);
    }

    // Get all elections
    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    // Get election by ID
    public Election getElectionById(Long id) {
        return electionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Election not found for id: " + id));
    }

    // Update election by ID
    public Election updateElection(Long id, Election electionDetails) {
        Election election = getElectionById(id);
        election.setName(electionDetails.getName());
        // Make sure to properly manage Polls
        // You might need to update or clear existing Polls
        election.setPolls(electionDetails.getPolls());
        return electionRepository.save(election);
    }

    // Delete election by ID
    public void deleteElection(Long id) {
        Election election = getElectionById(id);
        electionRepository.delete(election);
    }
}
