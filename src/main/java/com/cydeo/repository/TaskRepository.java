package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("Select count(t) from Task t Where t.project.projectCode = ?1 and t.taskStatus <> 'COMPLETE'")
    Integer totalNonCompletedTasks(String projectCode);

    @Query(value = "select count(*) from tasks t join projects p on t.project_id = p.id" +
            " where p.project_code = ?1 AND t.task_status = 'COMPLETE'",
            nativeQuery = true)
    Integer totalCompletedTasks(String projectCode);

    // This is a derived query
    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status taskStatus, User loggedInEmployee);

    List<Task> findAllByTaskStatusAndAssignedEmployee(Status taskStatus, User loggedInEmployee);
}
