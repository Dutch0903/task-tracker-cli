package com.task_tracker_cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_tracker_cli.exception.FailedToConvertTasksToJsonException;
import com.task_tracker_cli.exception.FailedToWriteToFileException;
import com.task_tracker_cli.model.Task;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class TaskWriter {

    public void write(File file, List<Task> tasks) throws FailedToConvertTasksToJsonException, FailedToWriteToFileException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(tasks);

            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (JsonProcessingException e) {
            throw new FailedToConvertTasksToJsonException(e);
        } catch (IOException e) {
            throw new FailedToWriteToFileException(e);
        }
    }
}
