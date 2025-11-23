package com.task_tracker_cli.command;

import com.task_tracker_cli.exception.InvalidStateException;
import com.task_tracker_cli.service.TaskManagerService;
import com.task_tracker_cli.type.TaskState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskManagerTest {
    @Mock
    private TaskManagerService taskManagerService;

    @Test
    public void test_create_calls_task_manager_service() {
        String description = "Description";

        TaskManager taskManager = this.getTaskManager();

        taskManager.create(description);

        verify(taskManagerService, times(1)).create(description);
    }

    @Test
    public void test_delete_calls_task_manager_service() {
        int id = 1;

        TaskManager taskManager = this.getTaskManager();

        taskManager.delete(id);

        verify(taskManagerService, times(1)).delete(id);
    }

    @Test
    public void test_update_calls_task_manager_service() {
        int id = 1;
        String description = "Description";

        TaskManager taskManager = this.getTaskManager();

        taskManager.update(id, description);

        verify(taskManagerService, times(1)).update(id, description);
    }

    @Test
    public void test_markInProgress_calls_task_manager_service() {
        int id = 1;

        TaskManager taskManager = this.getTaskManager();

        taskManager.markInProgress(id);

        verify(taskManagerService, times(1)).changeState(id, TaskState.IN_PROGRESS);
    }

    @Test
    public void test_markDone_calls_task_manager_service() {
        int id = 1;

        TaskManager taskManager = this.getTaskManager();

        taskManager.markDone(id);

        verify(taskManagerService, times(1)).changeState(id, TaskState.DONE);
    }

    @Test
    public void test_list_with_state_all_will_retrieve_all_tasks() {
        String state = "all";

        TaskManager taskManager = this.getTaskManager();
        try {
            taskManager.list(state);

            verify(taskManagerService, times(1)).listAllTasks();
        } catch (InvalidStateException e) {
            fail();
        }
    }

    @Test
    public void test_list_with_specific_state_will_retrieve_only_task_with_that_state() {
        String state = "done";

        TaskManager taskManager = this.getTaskManager();

        try {
            taskManager.list(state);

            verify(taskManagerService, times(1)).listTaskWithState(TaskState.DONE);
        } catch (InvalidStateException e) {
            fail();
        }
    }

    @Test
    public void test_list_with_invalid_state_throws_exception() {
        String state = "invalid";

        TaskManager taskManager = this.getTaskManager();

        try {
            taskManager.list(state);
            fail();
        } catch (InvalidStateException e) {
            assertEquals("Invalid state: " + state, e.getMessage());
        }
    }

    private TaskManager getTaskManager() {
        return new TaskManager(taskManagerService);
    }
}
