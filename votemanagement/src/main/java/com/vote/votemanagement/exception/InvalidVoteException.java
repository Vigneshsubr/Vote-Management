package com.vote.votemanagement.exception;

public class InvalidVoteException extends RuntimeException {
    public InvalidVoteException(String message) {
        super(message);
    }
}

