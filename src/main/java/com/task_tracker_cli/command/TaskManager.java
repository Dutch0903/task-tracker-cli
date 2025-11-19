package com.task_tracker_cli.command;

import com.task_tracker_cli.service.TaskManagerService;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Task Management")
public class TaskManager {

    private final TaskManagerService taskManagerService;

    public TaskManager(TaskManagerService taskManagerService) {
        this.taskManagerService = taskManagerService;
    }

    @ShellMethod("List all tasks")
    public void list() {
        taskManagerService.listAllTasks();
    }

    @ShellMethod("Create a new task")
    public void create(String description) {
        taskManagerService.create(description);
    }

    @ShellMethod("Delete a task")
    public void delete(int id) {
        taskManagerService.delete(id);
    }

    @ShellMethod("Update a task")
    public void update(int id, String description) {
        taskManagerService.update(id, description);
    }
}
