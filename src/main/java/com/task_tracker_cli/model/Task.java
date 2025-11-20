package com.task_tracker_cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.task_tracker_cli.type.TaskState;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Task {
    @JsonKey
    private final int id;
    private String description;
    private TaskState state;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @JsonCreator
    public Task(
            @JsonProperty("id")
            int id,
            @JsonProperty("description")
            String description,
            @JsonProperty("state")
            TaskState state,
            @JsonProperty("createdAt")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            OffsetDateTime createdAt,
            @JsonProperty("updatedAt")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.description = description;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = OffsetDateTime.now();
    }

    public void markAsInProgress() {
        this.state = TaskState.IN_PROGRESS;
        this.updatedAt = OffsetDateTime.now();
    }

    public void markAsDone() {
        this.state = TaskState.DONE;
        this.updatedAt = OffsetDateTime.now();
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
