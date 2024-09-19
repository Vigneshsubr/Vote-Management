package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.ResultDto;
import com.vote.votemanagement.exception.CustomException;
import com.vote.votemanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/poll/{pollId}")
    public ResponseEntity<?> getPollResults(@PathVariable Long pollId) {
        try {
            List<ResultDto> results = resultService.calculatePollResults(pollId);
            return ResponseEntity.ok(results);
        } catch (CustomException e) {
            // Return a 409 Conflict response when the results are already calculated
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
