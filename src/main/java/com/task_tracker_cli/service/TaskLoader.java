package com.task_tracker_cli.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_tracker_cli.exception.FailedToLoadTasksException;
import com.task_tracker_cli.model.Task;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TaskLoader {

    public List<Task> load(String fileName) throws FailedToLoadTasksException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);

        List<Task> tasks = List.of();
        try {
            tasks = objectMapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            throw new FailedToLoadTasksException(e);
        }

        return tasks;
    }
}
