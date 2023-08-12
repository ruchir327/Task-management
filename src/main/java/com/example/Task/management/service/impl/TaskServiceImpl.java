package com.example.Task.management.service.impl;

import com.example.Task.management.dto.TaskDto;
import com.example.Task.management.exception.ResourceNotFoundException;
import com.example.Task.management.model.Task;
import com.example.Task.management.repository.TaskRepository;
import com.example.Task.management.repository.UserRepository;
import com.example.Task.management.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

//    @Override
//    public TodoDto addTask(TaskDto taskDto) {
//
////         convert TaskDto in task Jpa entity
//        Task task = modelMapper.map(taskDto, Task.class);
//
//        // Task Jpa entity
//        Task savedTodo = taskRepository.save(task);
//
//        // Convert saved Task Jpa entity object into TaskDto object
//
//        TodoDto savedTaskDto = modelMapper.map(savedTask, TaskDto.class);
//
//        return savedTaskDto;
//    }
@Override
public TaskDto addTask(TaskDto taskDto, String assignedBy) {
    // Convert TodoDto to Task JPA entity
    Task task = modelMapper.map(taskDto, Task.class);

    // Set the assigned user and assigned by fields to the task
    task.setAssignedUser(taskDto.getAssignedUser()); // The assigned user comes from the request body
    task.setAssignedBy(assignedBy); // The assigned by user comes from the controller

    // Save the task
    Task savedTodo = taskRepository.save(task);

    // Convert saved Task JPA entity object into TaskDto object
    TaskDto savedTaskDto = modelMapper.map(savedTodo, TaskDto.class);

    return savedTaskDto;
}

    @Override
    public TaskDto getTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id:" + id));

        return modelMapper.map(task, TaskDto.class);
    }
    @Override
    public List<TaskDto> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();

        return tasks.stream().map((task) -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, Long id, String assignedBy) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        task.setAssignedUser(taskDto.getAssignedUser()); // Update assigned user
        task.setAssignedBy(assignedBy); // Set the assignedBy field to the username of the authenticated user

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }
    @Override
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        taskRepository.delete(task);
    }

    @Override
    public TaskDto completeTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        task.setCompleted(Boolean.TRUE);

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public TaskDto inCompleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        task.setCompleted(Boolean.FALSE);

        Task updatedTodo = taskRepository.save(task);

        return modelMapper.map(updatedTodo, TaskDto.class);
    }
    // ...



}
