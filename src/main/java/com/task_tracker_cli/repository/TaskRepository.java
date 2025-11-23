package com.task_tracker_cli.repository;

import com.task_tracker_cli.exception.FailedToLoadTasksException;
import com.task_tracker_cli.exception.FailedToSaveTasksException;
import com.task_tracker_cli.model.Task;
import com.task_tracker_cli.service.TaskLoader;
import com.task_tracker_cli.service.TaskWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

@Repository
public class TaskRepository {
    private final File file;
    private Map<Integer, Task> tasks;

    private final TaskLoader taskLoader;
    private final TaskWriter taskWriter;

    public TaskRepository(TaskLoader taskLoader, TaskWriter taskWriter) throws FailedToLoadTasksException {
        this.taskLoader = taskLoader;
        this.taskWriter = taskWriter;

        file = new File("tasks.json");

        loadTasks();
    }

    public Map<Integer, Task> getAll() {
        return this.tasks;
    }

    public int getNewId() {
        OptionalInt highestId = this.tasks.values().stream().mapToInt(Task::getId).max();

        return highestId.isEmpty() ? 1 : highestId.getAsInt() + 1;
    }

    public void save(Task task) throws FailedToSaveTasksException {
        this.tasks.put(task.getId(), task);

        this.saveTasks();
    }

    public void delete(int id) throws FailedToSaveTasksException {
        this.tasks.remove(id);

        this.saveTasks();
    }

    public Task findById(int id) {
        return this.tasks.getOrDefault(id, null);
    }

    private void saveTasks() throws FailedToSaveTasksException {
        this.taskWriter.write(this.file, this.tasks);
    }

    private void loadTasks() throws FailedToLoadTasksException {
        this.tasks = this.taskLoader.load(this.file);
    }
}
