package com.task_tracker_cli.service;

import com.task_tracker_cli.TaskState;
import com.task_tracker_cli.exception.FailedToConvertTasksToJsonException;
import com.task_tracker_cli.exception.FailedToLoadTasksException;
import com.task_tracker_cli.exception.FailedToWriteToFileException;
import com.task_tracker_cli.model.Task;
import com.task_tracker_cli.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskManagerService {

    private final TaskRepository taskRepository;

    public TaskManagerService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void listAllTasks() {
        try {
            List<Task> tasks = this.taskRepository.getAll();

            tasks.forEach(System.out::println);
        } catch(FailedToLoadTasksException e) {
            System.out.println("Failed to load tasks!");
        }
    }

    public void create(String description) {
        int newId = this.taskRepository.getNewId();

        Task task = new Task(newId, description, TaskState.TODO);

        try {
            this.taskRepository.save(task);

            System.out.println("Created new task: " + task);
        } catch (FailedToConvertTasksToJsonException | FailedToWriteToFileException e) {
            System.out.println("Failed to save task");
        }
    }

    public void delete(int id) {
        try {
            this.taskRepository.delete(id);
        } catch (FailedToConvertTasksToJsonException | FailedToWriteToFileException e) {
            System.out.println("Failed to delete task");
        }
    }
}
