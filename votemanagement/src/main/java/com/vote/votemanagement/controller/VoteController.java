package com.vote.votemanagement.controller;

import com.vote.votemanagement.config.TokenProvider;
import com.vote.votemanagement.dto.VoteRequest;
import com.vote.votemanagement.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/cast")
    public ResponseEntity<Map<String, String>> castVote(HttpServletRequest request, @RequestBody VoteRequest voteRequest) {
        String token = tokenProvider.recoverToken(request);
        if (token == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized: Missing token."));
        }

        String userIdString = String.valueOf(tokenProvider.getUserIdFromToken(token));
        if (!userIdString.matches("\\d+")) {
            return ResponseEntity.status(400).body(Map.of("error", "Invalid user ID format in token."));
        }

        Long userId = Long.parseLong(userIdString);
        voteService.castVote(userId, voteRequest.getPollId(), voteRequest.getCandidateId(), voteRequest.getElectionId());

        return ResponseEntity.ok(Map.of("message", "Vote successfully cast."));
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
