package com.example.Task.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private String assignedUser;



    private String assignedBy; // New field to store the name of the person who assigned the task

    public String getAssignedUser() {
        return this.assignedUser;
    }



}
