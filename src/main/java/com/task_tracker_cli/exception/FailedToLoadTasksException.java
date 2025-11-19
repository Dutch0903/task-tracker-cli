package com.task_tracker_cli.exception;

public class FailedToLoadTasksException extends Exception {
    public FailedToLoadTasksException(Exception cause) {
        super("Failed to load tasks from storage", cause);
    }
}
