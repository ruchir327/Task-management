package com.example.Task.management.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Task title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Task description should have at least 10 characters")
    private String description;
    @NonNull
    private boolean completed;
    @NonNull
    private String assignedUser;
    @NonNull
    private String assignedBy;




}
