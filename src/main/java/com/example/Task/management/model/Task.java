package com.example.Task.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;
    private boolean completed;
    private String assignedUser;
    private String assignedBy;
    public String getAssignedUser() {
        return assignedUser;
    }
    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy=assignedBy;
    }
}