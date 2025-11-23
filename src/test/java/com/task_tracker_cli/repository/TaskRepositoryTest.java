package com.task_tracker_cli.repository;

import com.task_tracker_cli.exception.FailedToLoadTasksException;
import com.task_tracker_cli.model.Task;
import com.task_tracker_cli.service.TaskLoader;
import com.task_tracker_cli.service.TaskWriter;
import com.task_tracker_cli.type.TaskState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryTest {

    @Mock
    private TaskLoader taskLoader;

    @Mock
    private TaskWriter taskWriter;

    @Test
    public void test_getNewId_return_one_when_no_tasks_exists() {
        TaskRepository taskRepository = this.createTaskRepository(new HashMap<>());

        int id = taskRepository.getNewId();

        assertEquals(1, id);
    }

    @Test
    public void test_getNewId_return_highest_id_plus_one_when_tasks_exists() {
        Map<Integer, Task> tasks = new HashMap<>();
        tasks.put(1, new Task(1, "Task", TaskState.TODO));

        TaskRepository taskRepository = this.createTaskRepository(tasks);

        assertEquals(2, taskRepository.getNewId());
    }

    @Test
    public void test_getAll_returns_all_tasks() {
        Map<Integer, Task> tasks = new HashMap<>();
        Task task1 = new Task(1, "Task", TaskState.TODO);
        Task task2 = new Task(2, "Second", TaskState.IN_PROGRESS);

        tasks.put(task1.getId(), task1);
        tasks.put(task2.getId(), task2);

        TaskRepository taskRepository = this.createTaskRepository(tasks);

        assertEquals(tasks, taskRepository.getAll());
    }

    @Test
    public void test_findById_returns_null_when_task_is_not_found() {
        Map<Integer, Task> tasks = new HashMap<>();
        Task task = new Task(1, "Task", TaskState.TODO);

        tasks.put(task.getId(), task);

        TaskRepository taskRepository = this.createTaskRepository(tasks);

        assertNull(taskRepository.findById(999));
    }

    @Test
    public void test_findById_returns_matched_task() {
        Map<Integer, Task> tasks = new HashMap<>();
        Task task = new Task(1, "Task", TaskState.TODO);

        tasks.put(task.getId(), task);

        TaskRepository taskRepository = this.createTaskRepository(tasks);

        assertEquals(task, taskRepository.findById(task.getId()));
    }

    @Test
    public void test_save_saves_tasks_to_file_and_adds_task_to_map() {
        Task task = new Task(1, "Task", TaskState.TODO);

        TaskRepository taskRepository = createTaskRepository(new HashMap<>());

        try {
            taskRepository.save(task);

            verify(taskWriter, times(1)).write(any(File.class), any());

            assertEquals(Map.of(task.getId(), task), taskRepository.getAll());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_delete_saves_remaining_tasks_to_file_and_remove_task_from_map() {
        Map<Integer, Task> tasks = new HashMap<>();
        Task task1 = new Task(1, "Task", TaskState.TODO);
        Task task2 = new Task(2, "Second", TaskState.IN_PROGRESS);

        tasks.put(task1.getId(), task1);
        tasks.put(task2.getId(), task2);

        TaskRepository taskRepository = this.createTaskRepository(tasks);

        try {
            taskRepository.delete(2);

            verify(taskWriter, times(1)).write(any(File.class), anyMap());

            assertEquals(Map.of(task1.getId(), task1), taskRepository.getAll());
        } catch (Exception e) {
            fail();
        }
    }

    private TaskRepository createTaskRepository(Map<Integer, Task> tasks) {
        try {
            when(taskLoader.load(any(File.class))).thenReturn(
                    tasks
            );

            return new TaskRepository(taskLoader, taskWriter);
        } catch (FailedToLoadTasksException e) {
            throw new RuntimeException(e);
        }
    }
}
