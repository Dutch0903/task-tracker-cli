package com.task_tracker_cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.task_tracker_cli.TaskState;

import java.util.Objects;

public class Task {
    private final int id;
    private String description;
    private TaskState state;

    @JsonCreator
    public Task(
            @JsonProperty("id") int id,
            @JsonProperty("description") String description,
            @JsonProperty("state") TaskState state
    ) {
        this.id = id;
        this.description = description;
        this.state = state;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public TaskState getState() {
        return this.state;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void markAsInProgress() {
        this.state = TaskState.IN_PROGRESS;
    }

    public void markAsDone() {
        this.state = TaskState.DONE;
    }

    @Override
    public String toString() {
        return this.state + ": " + this.description + " (ID: " + this.id + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Task task)) {
            return false;
        }

        return task.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, state);
    }
}
