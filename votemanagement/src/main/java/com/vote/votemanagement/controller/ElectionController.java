package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    // Create a new election
    @PostMapping
    public ResponseEntity<Election> createElection(@RequestBody Election election) {
        Election createdElection = electionService.createElection(election);
        return ResponseEntity.ok(createdElection);
    }

    // Get all elections
    @GetMapping
    public ResponseEntity<List<Election>> getAllElections() {
        List<Election> elections = electionService.getAllElections();
        return ResponseEntity.ok(elections);
    }

    // Get election by ID
    @GetMapping("/{id}")
    public ResponseEntity<Election> getElectionById(@PathVariable Long id) {
        Election election = electionService.getElectionById(id);
        return ResponseEntity.ok(election);
    }

    // Update election by ID
    @PutMapping("/{id}")
    public ResponseEntity<Election> updateElection(@PathVariable Long id, @RequestBody Election election) {
        Election updatedElection = electionService.updateElection(id, election);
        return ResponseEntity.ok(updatedElection);
    }

    // Delete election by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long id) {
        electionService.deleteElection(id);
        return ResponseEntity.noContent().build();
    }
}
