package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.VoteDTO;
import com.vote.votemanagement.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<?> createVote(@RequestBody VoteDTO voteDTO) {
        return ResponseEntity.ok(voteService.createVote(voteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVoteById(@PathVariable Long id) {
        return ResponseEntity.ok(voteService.getVoteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable Long id) {
        voteService.deleteVote(id);
        return ResponseEntity.ok().build();
    }
}
