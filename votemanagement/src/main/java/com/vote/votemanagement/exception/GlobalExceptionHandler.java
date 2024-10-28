package com.vote.votemanagement.exception;

import com.vote.votemanagement.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ResponseDTO> handleInvalidJwtException(InvalidJwtException ex) {
        logger.error("Invalid JWT token: {}", ex.getMessage());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(ex.getMessage());
        responseDTO.setStatusCode(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
    }

    // Handle other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);  // Log full exception details
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("An unexpected error occurred");
        responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidVoteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidVoteException(InvalidVoteException ex) {
        logger.error("Invalid vote: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("User not found: {}", ex.getMessage());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(ex.getMessage());
        responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDTO> handleAdminNotFoundException(AdminNotFoundException ex) {
        logger.error("Admin not found: {}", ex.getMessage());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(ex.getMessage());
        responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElectionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDTO> handleElectionNotFoundException(ElectionNotFoundException ex) {
        logger.error("Election not found: {}", ex.getMessage());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(ex.getMessage());
        responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleCandidateNotFoundException(CandidateNotFoundException ex) {
        logger.error("Candidate not found: {}", ex.getMessage());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(ex.getMessage());
        responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PollNotFoundException.class)
    public ResponseEntity<ResponseDTO> handlePollNotFoundException(PollNotFoundException ex) {
        logger.error("Poll not found: {}", ex.getMessage());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(ex.getMessage());
        responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(VoteAlreadyExistsException.class)
    public ResponseEntity<String> handleVoteAlreadyExistsException(VoteAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
