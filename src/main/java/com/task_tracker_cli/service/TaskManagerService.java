package com.task_tracker_cli.service;

import com.task_tracker_cli.type.TaskState;
import com.task_tracker_cli.exception.FailedToSaveTasksException;
import com.task_tracker_cli.model.Task;
import com.task_tracker_cli.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TaskManagerService {

    private final TaskRepository taskRepository;

    public TaskManagerService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void listAllTasks() {
        Map<Integer, Task> tasks = this.taskRepository.getAll();

        tasks.values().forEach(System.out::println);
    }

    public void listTaskWithState(TaskState state) {
        List<Task> tasks = this.taskRepository.getAll().values().stream().toList();

        tasks.stream().filter(task -> task.getState() == state).forEach(System.out::println);
    }

    public void create(String description) {
        int newId = this.taskRepository.getNewId();

        Task task = new Task(newId, description, TaskState.TODO, OffsetDateTime.now(), OffsetDateTime.now());

        try {
            this.taskRepository.save(task);

            System.out.println("Created new task: " + task);
        } catch (FailedToSaveTasksException e) {
            System.out.println("Failed to save task");
        }
    }

    public void delete(int id) {
        try {
            this.taskRepository.delete(id);
        } catch (FailedToSaveTasksException e) {
            System.out.println("Failed to delete task");
        }
    }

    public void update(int id, String description) {
        Task task = this.taskRepository.findById(id);

        if (task == null) {
            System.out.println("No task found with Id: " + id);
            return;
        }
        task.updateDescription(description);

        try {
            this.taskRepository.save(task);
        } catch (FailedToSaveTasksException e) {
            System.out.println("Failed to save updated task");
        }
    }

    public void changeState(int id, TaskState state) {
        Task task = this.taskRepository.findById(id);

        if (task == null) {
            System.out.println("No task found with Id: " + id);
            return;
        }

        switch (state) {
            case IN_PROGRESS -> task.markAsInProgress();
            case DONE -> task.markAsDone();
            default -> {
                return;
            }
        }

        try {
            this.taskRepository.save(task);
        } catch (FailedToSaveTasksException e) {
            System.out.println("Failed to save updated task");
        }
    }
}
