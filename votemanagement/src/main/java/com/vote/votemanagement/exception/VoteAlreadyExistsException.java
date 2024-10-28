package com.vote.votemanagement.exception;

public class VoteAlreadyExistsException extends RuntimeException {
    public VoteAlreadyExistsException(String message) {
        super(message);
    }
}
