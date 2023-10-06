package com.cydeo.dto;

import com.cydeo.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class TaskDTO {

    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject;
    private String taskDetail;
    private Status taskStatus;
    private LocalDate assignedDate;

    public TaskDTO(ProjectDTO project, UserDTO assignedEmployee, String taskSubject, String taskDetail, Status taskStatus, LocalDate assignedDate) {
        this.project = project;
        this.assignedEmployee = assignedEmployee;
        this.taskSubject = taskSubject;
        this.taskDetail = taskDetail;
        this.taskStatus = taskStatus;
        this.assignedDate = assignedDate;
        this.id = UUID.randomUUID().getMostSignificantBits();
    }
}
