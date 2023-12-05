package com.breakingadv.slipserver.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String userId) {
        super("User with id " + userId + " not found.");
    }
}
