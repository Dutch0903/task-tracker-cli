package com.task_tracker_cli.exception;

public class FailedToLoadTasksException extends Exception {
    public FailedToLoadTasksException(Throwable cause) {
        super("Failed to load tasks from storage", cause);
    }
}
