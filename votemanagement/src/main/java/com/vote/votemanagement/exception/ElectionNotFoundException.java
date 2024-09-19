package com.vote.votemanagement.exception;

public class ElectionNotFoundException extends RuntimeException {
    public ElectionNotFoundException(String message) {
        super(message);
    }
}

