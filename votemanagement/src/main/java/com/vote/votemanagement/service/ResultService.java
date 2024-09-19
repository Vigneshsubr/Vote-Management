package com.vote.votemanagement.service;

import com.vote.votemanagement.dto.ResultDto;
import com.vote.votemanagement.entity.Result;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.exception.CustomException;
import com.vote.votemanagement.repository.ResultRepository;
import com.vote.votemanagement.repository.PollRepository;
import com.vote.votemanagement.repository.CandidateRepository;
import com.vote.votemanagement.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    // Method to calculate results for a specific poll
    public List<ResultDto> calculatePollResults(Long pollId) throws CustomException {
        // Check if results for this poll have already been calculated
        boolean isResultAlreadyCalculated = resultRepository.existsByPollId(pollId);
        if (isResultAlreadyCalculated) {
            throw new CustomException("Poll results have already been calculated.");
        }

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new CustomException("Poll not found"));

        List<Candidate> candidates = candidateRepository.findByPollId(pollId);
        long totalVotes = voteRepository.countByPollId(pollId);

        if (totalVotes == 0) {
            throw new CustomException("No votes found for this poll.");
        }

        List<ResultDto> pollResults = new ArrayList<>();

        for (Candidate candidate : candidates) {
            long candidateVotes = voteRepository.countByCandidateIdAndPollId(candidate.getId(), pollId);
            double votePercentage = ((double) candidateVotes / totalVotes) * 100;

            Result result = new Result();
            result.setPoll(poll);
            result.setCandidate(candidate);
            result.setTotalVotes((int) candidateVotes);
            result.setVotePercentage(votePercentage);
            result.setCalculatedAt(LocalDateTime.now());

            // Save result for each candidate
            resultRepository.save(result);

            // Prepare result DTO
            ResultDto resultDto = new ResultDto();
            resultDto.setPollId(poll.getId());
            resultDto.setCandidateId(candidate.getId());
            resultDto.setCandidateName(candidate.getUsername());
            resultDto.setTotalVotes((int) candidateVotes);
            resultDto.setVotePercentage(votePercentage);

            pollResults.add(resultDto);
        }

        return pollResults;
    }
}
