package com.example.Task.management.controller;

import com.example.Task.management.dto.TaskDto;
import com.example.Task.management.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("api/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    // Build Add Todo REST API

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
//
//        TodoDto savedTodo = todoService.addTodo(todoDto);
//
//        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
//    }
@PreAuthorize("hasRole('ADMIN')")
@PostMapping
public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) {
    // Get the currently logged-in user's username (the assigned by user)
    String assignedBy = SecurityContextHolder.getContext().getAuthentication().getName();

    // Call the addTodo method and pass the assignedBy user from the controller
    TaskDto savedTask = taskService.addTask(taskDto, assignedBy);

    return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
}

    // Build Get Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") Long taskId){
        TaskDto todoDto = taskService.getTask(taskId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // Build Get All Todos REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        List<TaskDto> todos = taskService.getAllTasks();
        //return new ResponseEntity<>(todos, HttpStatus.OK);
        return ResponseEntity.ok(todos);
    }

    // Build Update Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("{id}")
    public ResponseEntity<TaskDto> updateTasks(
            @RequestBody TaskDto taskDto,
            @PathVariable("id") Long taskId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String assignedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        TaskDto updatedTodo = taskService.updateTask(taskDto, taskId, assignedBy);
        return ResponseEntity.ok(updatedTodo);
    }

    // Build Delete Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long todoId){
        taskService.deleteTask(todoId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }

    // Build Complete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable("id") Long todoId){
        TaskDto updatedTask = taskService.completeTask(todoId);
        return ResponseEntity.ok(updatedTask);
    }

    // Build In Complete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TaskDto> inCompleteTodo(@PathVariable("id") Long taskId){
        TaskDto updatedTodo = taskService.inCompleteTask(taskId);
        return ResponseEntity.ok(updatedTodo);
    }


}