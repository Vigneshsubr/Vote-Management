package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.ResultDto;
import com.vote.votemanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/poll/{pollId}")
    public ResponseEntity<List<ResultDto>> getPollResults(@PathVariable Long pollId) {
        try {
            List<ResultDto> results = resultService.calculatePollResults(pollId);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
