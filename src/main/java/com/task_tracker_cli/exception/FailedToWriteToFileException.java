package com.task_tracker_cli.exception;

public class FailedToWriteToFileException extends Exception {
    public FailedToWriteToFileException(Throwable cause) {
        super("Failed to write to file", cause);
    }
}
