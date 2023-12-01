package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {
    TaskDTO findById(Long id);
    List<TaskDTO> listAllTasks();
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);
    Integer totalNonCompletedTasks(String projectCode);
    Integer totalCompletedTasks(String projectCode);

    void deleteByProject(ProjectDTO projectDTO);

    void completeByProject(ProjectDTO project);

    List<TaskDTO> listAllTasksByStatusIsNot(Status taskStatus);

    void updateStatus(TaskDTO taskDTO);

    List<TaskDTO> listAllTasksByStatus(Status taskStatus);

    /**
     * This method is designed to support a decision about the eligibility of an Employee for deletion
     * @param user
     * @return list of TaskDTO
     */
    List<TaskDTO> readAllByAssignedEmployee(User user);
}
