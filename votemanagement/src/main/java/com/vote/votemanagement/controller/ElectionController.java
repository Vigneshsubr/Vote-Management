package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    // Get all elections
    @GetMapping
    public List<Election> getAllElections() {
        return electionService.getAllElections();
    }

    // Get election by ID
    @GetMapping("/{id}")
    public ResponseEntity<Election> getElectionById(@PathVariable Long id) {
        Optional<Election> election = electionService.getElectionById(id);
        return election.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new election
    @PostMapping
    public ResponseEntity<Election> createElection(@RequestBody Election election) {
        Election createdElection = electionService.createElection(election);
        return ResponseEntity.ok(createdElection);
    }

    // Delete election by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long id) {
        electionService.deleteElection(id);
        return ResponseEntity.noContent().build();
    }
}
