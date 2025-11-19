package com.task_tracker_cli.exception;

public class FailedToSaveTasksException extends Exception {
    public FailedToSaveTasksException(Throwable cause) {
        super("Failed to save tasks", cause);
    }
}
