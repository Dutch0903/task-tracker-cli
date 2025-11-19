package com.task_tracker_cli.repository;

import com.task_tracker_cli.exception.FailedToLoadTasksException;
import com.task_tracker_cli.model.Task;
import com.task_tracker_cli.service.TaskLoader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    private final String fileName = "tasks.json";
    private List<Task> tasks;

    private final TaskLoader taskLoader;

    public TaskRepository(TaskLoader taskLoader) {
        this.taskLoader = taskLoader;

        tasks = new ArrayList<>();
    }

    public List<Task> getAll() throws FailedToLoadTasksException {
        if (this.tasks.isEmpty()) {
            this.tasks = taskLoader.load(fileName);
        }

        return this.tasks;
    }
}
