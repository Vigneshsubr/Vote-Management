package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.service.CandidateService;
import com.vote.votemanagement.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private CandidateService candidateService;

    // Get all polls
    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    // Get poll by ID
    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPollById(@PathVariable Long id) {
        Optional<Poll> poll = pollService.getPollById(id);
        return poll.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new poll
    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        Poll createdPoll = pollService.createPoll(poll);
        return ResponseEntity.ok(createdPoll);
    }


    // Delete poll by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{pollId}/candidates")
    public ResponseEntity<List<Candidate>> fetchCandidatesByPollId(@PathVariable Long pollId) {
        List<Candidate> candidates = candidateService.getCandidatesByPollId(pollId);
        return ResponseEntity.ok(candidates);
    }
}
