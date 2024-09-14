package com.vote.votemanagement.service;

import com.vote.votemanagement.entity.Result;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
import com.vote.votemanagement.repository.ResultRepository;
import com.vote.votemanagement.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private VoteRepository voteRepository;

    // Calculate total votes and determine winner
    public void calculateResults(Poll poll) {
        // Get vote counts for each candidate
        List<Object[]> voteCounts = voteRepository.countVotesByCandidate(poll.getId());

        Candidate winner = null;
        int maxVotes = 0;

        for (Object[] voteCount : voteCounts) {
            Long candidateId = (Long) voteCount[0];
            int totalVotes = ((Long) voteCount[1]).intValue();

            // Fetch candidate entity (you can use candidate service or repository)
            Candidate candidate = new Candidate(); // Use actual fetch logic here
            candidate.setId(candidateId);

            // Save result for each candidate
            Result result = new Result();
            result.setPoll(poll);
            result.setCandidate(candidate);
            result.setTotalVotes(totalVotes);
            result.setWinner(false);  // Set initially false

            resultRepository.save(result);

            // Check if this candidate has more votes than the current winner
            if (totalVotes > maxVotes) {
                maxVotes = totalVotes;
                winner = candidate;
            }
        }

        // Update the winner
        if (winner != null) {
            Optional<Result> resultOptional = resultRepository.findByPollIdAndCandidateId(poll.getId(), winner.getId());
            if (resultOptional.isPresent()) {
                Result winningResult = resultOptional.get();
                winningResult.setWinner(true);  // Set winner true
                resultRepository.save(winningResult);
            }
        }
    }
}
