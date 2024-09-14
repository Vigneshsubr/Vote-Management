package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Vote;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public Vote castVote(
            @RequestBody User voter,
            @RequestParam Long pollId,
            @RequestParam Long candidateId) throws Exception {

        Poll poll = new Poll(); // replace with pollService.findPollById(pollId).get();
        poll.setId(pollId);

        Candidate candidate = new Candidate(); // replace with candidateService.findCandidateById(candidateId).get();
        candidate.setId(candidateId);

        return voteService.castVote(voter, poll, candidate);
    }
}
