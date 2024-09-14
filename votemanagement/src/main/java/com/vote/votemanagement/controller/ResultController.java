package com.vote.votemanagement.controller;

import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    // Endpoint to calculate results for a poll
    @PostMapping("/calculate/{pollId}")
    public void calculateResults(@PathVariable Long pollId) {
        Poll poll = new Poll(); // Fetch the poll by pollId (use actual service/repository logic)
        poll.setId(pollId);
        resultService.calculateResults(poll);
    }
}
