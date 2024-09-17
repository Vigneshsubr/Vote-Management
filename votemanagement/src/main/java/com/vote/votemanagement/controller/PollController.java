package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPollById(@PathVariable Long id) {
        Optional<Poll> poll = pollService.getPollById(id);
        return poll.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
//
//   // Create a new poll
//   @PostMapping
//   public ResponseEntity<Poll> createPoll(@RequestBody Poll pollRequest) {
//       Poll createdPoll = pollService.createPoll(pollRequest.toPoll(), pollRequest.getId());
//       return ResponseEntity.ok(createdPoll);
//   }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
        return ResponseEntity.noContent().build();
    }
}
