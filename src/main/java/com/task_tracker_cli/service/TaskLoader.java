package com.task_tracker_cli.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_tracker_cli.exception.FailedToLoadTasksException;
import com.task_tracker_cli.model.Task;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskLoader {

    public Map<Integer, Task> load(File file) throws FailedToLoadTasksException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        Map<Integer, Task> tasks;
        try {
            List<Task> result = objectMapper.readValue(file, new TypeReference<>() {});

            tasks = result.stream().collect(Collectors.toMap(Task::getId, task -> task));
        } catch (IOException e) {
            throw new FailedToLoadTasksException(e);
        }

        return tasks;
    }
}
