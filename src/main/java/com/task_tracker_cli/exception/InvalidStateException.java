package com.task_tracker_cli.exception;

public class InvalidStateException extends Exception {
    public InvalidStateException(String invalidState) {
        super("Invalid state: " + invalidState);
    }
}
