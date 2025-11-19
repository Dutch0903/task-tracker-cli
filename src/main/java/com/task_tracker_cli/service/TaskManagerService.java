package com.task_tracker_cli.service;

import com.task_tracker_cli.exception.FailedToLoadTasksException;
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
}
