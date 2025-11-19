package com.task_tracker_cli.repository;

import com.task_tracker_cli.exception.FailedToConvertTasksToJsonException;
import com.task_tracker_cli.exception.FailedToLoadTasksException;
import com.task_tracker_cli.exception.FailedToWriteToFileException;
import com.task_tracker_cli.model.Task;
import com.task_tracker_cli.service.TaskLoader;
import com.task_tracker_cli.service.TaskWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.OptionalInt;

@Repository
public class TaskRepository {
    private final File file;
    private List<Task> tasks;

    private final TaskLoader taskLoader;
    private final TaskWriter taskWriter;

    public TaskRepository(TaskLoader taskLoader, TaskWriter taskWriter) throws FailedToLoadTasksException {
        this.taskLoader = taskLoader;
        this.taskWriter = taskWriter;

        file = new File("tasks.json");

        loadTasks();
    }

    public List<Task> getAll() throws FailedToLoadTasksException {
        return this.tasks;
    }

    public int getNewId() {
        OptionalInt highestId = this.tasks.stream().mapToInt(Task::getId).max();

        return highestId.isEmpty() ? 1 : highestId.getAsInt() + 1;
    }

    public void save(Task task) throws FailedToConvertTasksToJsonException, FailedToWriteToFileException {
        this.tasks.add(task);

        this.taskWriter.write(this.file, this.tasks);
    }

    private void loadTasks() throws FailedToLoadTasksException {
        this.tasks = this.taskLoader.load(this.file);
    }
}
