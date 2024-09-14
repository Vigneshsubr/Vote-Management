package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.savePoll(poll);
    }

    @GetMapping("/{id}")
    public Optional<Poll> getPollById(@PathVariable Long id) {
        return pollService.findPollById(id);
    }

    @PutMapping("/{id}")
    public Poll updatePoll(@PathVariable Long id, @RequestBody Poll updatedPoll) throws Exception {
        return pollService.savePoll(updatedPoll);
    }

    @DeleteMapping("/{id}")
    public void deletePoll(@PathVariable Long id) throws Exception {
        pollService.findPollById(id)
                .orElseThrow(() -> new Exception("Poll not found"));
    }
}

