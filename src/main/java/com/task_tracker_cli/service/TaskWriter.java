package com.task_tracker_cli.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_tracker_cli.exception.FailedToSaveTasksException;
import com.task_tracker_cli.model.Task;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Service
public class TaskWriter {

    public void write(File file, Map<Integer, Task> tasks) throws FailedToSaveTasksException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            String json = objectMapper.writeValueAsString(tasks.values());

            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new FailedToSaveTasksException(e);
        }
    }
}
