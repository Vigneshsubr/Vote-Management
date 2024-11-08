package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.ResponseDTO;
import com.vote.votemanagement.service.ResultService;
import com.vote.votemanagement.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    // Get Poll results by Poll ID
    @GetMapping("/poll/{pollId}")
    public ResponseEntity<ResponseDTO> getPollResults(@PathVariable Long pollId) {
        try {
            ResponseDTO response = resultService.getPollResults(pollId);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        } catch (CustomException e) {
            ResponseDTO response = new ResponseDTO(e.getMessage(), null, 409);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("An unexpected error occurred.", null, 500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST Method to manually create results for a poll
    @PostMapping("/poll/{pollId}")
    public ResponseEntity<ResponseDTO> createPollResults(@PathVariable Long pollId) {
        try {
            ResponseDTO response = resultService.createPollResults(pollId);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        } catch (CustomException e) {
            ResponseDTO response = new ResponseDTO(e.getMessage(), null, 409);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO("An unexpected error occurred.", null, 500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete Poll results by Poll ID
    @DeleteMapping("/{pollId}")
    public ResponseEntity<ResponseDTO> deletePollResults(@PathVariable Long pollId) {
        ResponseDTO response = resultService.deletePollResultsByPollId(pollId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
