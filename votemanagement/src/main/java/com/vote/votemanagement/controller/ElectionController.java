package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Election;
import com.vote.votemanagement.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping
    public Election createElection(@RequestBody Election election) {
        return electionService.createElection(election);
    }

    @GetMapping("/{id}")
    public Optional<Election> getElectionById(@PathVariable Long id) {
        return electionService.findElectionById(id);
    }

    @GetMapping
    public List<Election> getAllElections() {
        return electionService.findAllElections();
    }

    @PutMapping("/{id}")
    public Election updateElection(@PathVariable Long id, @RequestBody Election updatedElection) throws Exception {
        return electionService.updateElection(id, updatedElection);
    }

    @DeleteMapping("/{id}")
    public void deleteElection(@PathVariable Long id) throws Exception {
        electionService.deleteElection(id);
    }
}

