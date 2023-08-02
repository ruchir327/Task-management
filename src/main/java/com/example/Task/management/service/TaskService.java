package com.example.Task.management.service;

import com.example.Task.management.dto.TaskDto;
import com.example.Task.management.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto addTask(TaskDto taskDto, String assignedBy);

    TaskDto getTask(Long id);
    List<TaskDto> getAllTasks();
//
    TaskDto updateTask(TaskDto taskDto, Long id, String assignedBy);
    void deleteTask(Long id);

    TaskDto completeTask(Long id);

    TaskDto inCompleteTask(Long id);

}