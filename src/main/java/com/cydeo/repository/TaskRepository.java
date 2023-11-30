package com.cydeo.repository;

import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("Select count(t) from Task t Where t.project.projectCode = ?1 and t.taskStatus <> 'Completed'")
    Integer totalNonCompletedTasks(String projectCode);

    @Query(value = "select count(*) from tasks t join projects p on t.project_id = p.id" +
            " where p.project_code = ?1 AND t.task_status = 'COMPLETE'",
            nativeQuery = true)
    Integer totalCompletedTasks(String projectCode);
}
