package com.vote.votemanagement.controller;

import com.vote.votemanagement.config.TokenProvider;
import com.vote.votemanagement.dto.VoteRequest;
import com.vote.votemanagement.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;
    @Autowired
    private TokenProvider tokenProvider;


    @PostMapping("/cast")
    public ResponseEntity<String> castVote(HttpServletRequest request, @RequestBody VoteRequest voteRequest) {
        // Retrieve the token from the Authorization header
        String token = tokenProvider.recoverToken(request);
        if (token == null) {
            return ResponseEntity.status(401).body("Unauthorized: Missing token.");
        }

        // Retrieve userId from the token
        Long userId = tokenProvider.getUserIdFromToken(token);

        // Cast vote using the VoteService
        voteService.castVote(userId, voteRequest.getPollId(), voteRequest.getCandidateId());

        return ResponseEntity.ok("Vote successfully cast.");
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
