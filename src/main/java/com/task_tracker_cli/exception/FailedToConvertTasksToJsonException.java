package com.task_tracker_cli.exception;

public class FailedToConvertTasksToJsonException extends Exception {
    public FailedToConvertTasksToJsonException(Throwable cause) {
        super("Failed to convert list to json", cause);
    }
}
