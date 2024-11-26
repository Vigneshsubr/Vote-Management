package com.vote.votemanagement.service;

import com.vote.votemanagement.dto.ResponseDTO;
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
    public ResponseDTO calculatePollResults(Long pollId) throws CustomException {
        boolean isResultAlreadyCalculated = resultRepository.existsByPollId(pollId);
        if (isResultAlreadyCalculated) {
            return new ResponseDTO("Poll results have already been calculated.", null, 409);
        }

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new CustomException("Poll not found"));

        List<Candidate> candidates = candidateRepository.findByPollId(pollId);
        long totalVotes = voteRepository.countByPollId(pollId);

        if (totalVotes == 0) {
            return new ResponseDTO("No votes found for this poll.", null, 400);
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

            resultRepository.save(result);

            ResultDto resultDto = new ResultDto();
            resultDto.setPollId(poll.getId());
            resultDto.setCandidateId(candidate.getId());
            resultDto.setCandidateName(candidate.getName());
            resultDto.setTotalVotes((int) candidateVotes);
            resultDto.setVotePercentage(votePercentage);

            pollResults.add(resultDto);
        }

        return new ResponseDTO("Poll results calculated successfully.", pollResults, 200);
    }

    // Method to create or manually calculate and save poll results
    public ResponseDTO createPollResults(Long pollId) throws CustomException {
        return calculatePollResults(pollId);  // Reusing the same logic for simplicity
    }

    @Transactional
    public ResponseDTO deletePollResultsByPollId(Long pollId) {
        if (!resultRepository.existsByPollId(pollId)) {
            return new ResponseDTO("Poll ID not found: " + pollId, null, 404);
        }
        resultRepository.deleteByPollId(pollId);
        return new ResponseDTO("Poll results for Poll ID " + pollId + " have been deleted successfully.", null, 200);
    }

    // Method to fetch poll results
    public ResponseDTO getPollResults(Long pollId) throws CustomException {
        List<ResultDto> results = new ArrayList<>();
        List<Result> resultList = resultRepository.findByPollId(pollId);

        if (resultList.isEmpty()) {
            throw new CustomException("No results found for the given poll.");
        }

        for (Result result : resultList) {
            ResultDto dto = new ResultDto();
            dto.setPollId(result.getPoll().getId());
            dto.setPollTitle(result.getPoll().getPollName());
            dto.setCandidateId(result.getCandidate().getId());
            dto.setCandidateName(result.getCandidate().getName());
            dto.setTotalVotes(result.getTotalVotes());
            dto.setVotePercentage(result.getVotePercentage());
            dto.setCalculatedAt(result.getCalculatedAt());
            results.add(dto);
        }

        return new ResponseDTO("Poll results fetched successfully.", results, 200);
    }
}
