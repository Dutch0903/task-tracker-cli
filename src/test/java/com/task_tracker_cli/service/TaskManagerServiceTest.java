package com.task_tracker_cli.service;

import com.task_tracker_cli.model.Task;
import com.task_tracker_cli.repository.TaskRepository;
import com.task_tracker_cli.type.TaskState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskManagerServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskManagerService taskManagerService;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void test_listAllTasks_prints_all_tasks() {
        Task task1 = new Task(1, "Task", TaskState.TODO);
        Task task2 = new Task(2, "Second", TaskState.IN_PROGRESS);
        Map<Integer, Task> tasks = Map.of(task1.getId(), task1, task2.getId(), task2);

        when(taskRepository.getAll()).thenReturn(tasks);

        taskManagerService.listAllTasks();

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains(task1.toString()));
        assertTrue(output.contains(task2.toString()));
    }

    @Test
    public void test_listAllTasks_prints_nothing_when_there_are_no_tasks() {
        Map<Integer, Task> tasks = Map.of();

        when(taskRepository.getAll()).thenReturn(tasks);

        taskManagerService.listAllTasks();

        assertEquals("", outputStreamCaptor.toString());
    }

    @Test
    public void test_listTasksWithState_prints_nothing_where_there_are_no_tasks() {
        Map<Integer, Task> tasks = Map.of();

        when(taskRepository.getAll()).thenReturn(tasks);

        taskManagerService.listTaskWithState(TaskState.TODO);

        assertEquals("", outputStreamCaptor.toString());
    }

    @Test
    public void test_list_TasksWithState_only_prints_the_tasks_with_matching_states() {
        Task task1 = new Task(1, "Task", TaskState.TODO);
        Task task2 = new Task(2, "Second", TaskState.IN_PROGRESS);
        Map<Integer, Task> tasks = Map.of(task1.getId(), task1, task2.getId(), task2);

        when(taskRepository.getAll()).thenReturn(tasks);

        taskManagerService.listTaskWithState(TaskState.TODO);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains(task1.toString()));
        assertFalse(output.contains(task2.toString()));
    }

    @Test
    public void test_create_add_new_tasks_with_state_todo() {
        String description = "New Task";

        when(taskRepository.getNewId()).thenReturn(10);
        Task expectedTask = new Task(10, description, TaskState.TODO);

        try {
            taskManagerService.create(description);

            verify(taskRepository, times(1)).save(expectedTask);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_update_does_not_save_anything_when_no_task_is_found() {
        int taskId = 10;
        String newDescription = "New Description";

        when(taskRepository.findById(taskId)).thenReturn(null);

        try {
            taskManagerService.update(taskId, newDescription);

            verify(taskRepository, times(0)).save(any(Task.class));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_update_saves_the_updated_task() {
        int taskId = 10;
        String newDescription = "New Description";

        Task task = new Task(taskId, "Description", TaskState.TODO);
        Task updatedTask = new Task(taskId, newDescription, TaskState.TODO);

        when(taskRepository.findById(taskId)).thenReturn(task);
        try {
            taskManagerService.update(taskId, newDescription);

            verify(taskRepository, times(1)).save(updatedTask);
        } catch (Exception e) {
            fail();
        }
    }



    @Test
    public void test_changeState_does_not_save_anything_when_no_task_is_found() {
        int taskId = 10;

        when(taskRepository.findById(taskId)).thenReturn(null);

        try {
            taskManagerService.changeState(taskId, TaskState.IN_PROGRESS);

            verify(taskRepository, times(0)).save(any(Task.class));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_changeState_saves_the_updated_task() {
        int taskId = 10;
        String description = "Description";

        Task task = new Task(taskId, description, TaskState.TODO);
        Task updatedTask = new Task(taskId, description, TaskState.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(task);
        try {
            taskManagerService.changeState(taskId, TaskState.IN_PROGRESS);

            verify(taskRepository, times(1)).save(updatedTask);
        } catch (Exception e) {
            fail();
        }
    }
}
