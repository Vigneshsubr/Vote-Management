package com.vote.votemanagement.exception;

public class DuplicateVoteException extends RuntimeException{
    public DuplicateVoteException(String message) {
        super(message);
    }
}
