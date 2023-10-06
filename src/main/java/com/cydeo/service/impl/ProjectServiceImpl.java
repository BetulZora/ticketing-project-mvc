package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO object) {

        // This is not a value
        if(object.getStatus() == null){
            object.setStatus(Status.OPEN);
        }



        return super.save( object.getProjectCode(), object);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public ProjectDTO findById(String projectCode) {
        return super.findById(projectCode);
    }

    @Override
    public void deleteById(String projectCode) {
        super.deleteById(projectCode);

    }

    @Override
    public void update(ProjectDTO object) {
        ProjectDTO oldversion = findById(object.getProjectCode());

        if(object.getStatus() == null){
            // When updating, using the status of the object before it has been updated in the memory
            object.setStatus(oldversion.getStatus());
        }
        super.update(object.getProjectCode(), object);

    }

    @Override
    public void complete(ProjectDTO object) {
        object.setStatus(Status.COMPLETE);
        // Do not forget to save the object after the status change
        super.save(object.getProjectCode(), object);


    }

    @Override
    public List<ProjectDTO> findAllNonCompletedProjects() {
        return super.findAll().stream().filter(p->p.getStatus()!= Status.COMPLETE).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
        List<ProjectDTO> projectList =
                findAll()
                        .stream()
                        .filter(project -> project.getAssignedManager().equals(manager))
                        .map( project -> { // This operation is required to calculate the two missing fields.

                            List<TaskDTO> taskList = taskService.findTasksByManager(manager);

                            int completeTaskCounts = (int)taskList.stream().filter(t ->  t.getProject().equals(project)
                                    && t.getTaskStatus() == Status.COMPLETE).count();
                            int unfinishedTaskCounts = (int)taskList.stream().filter(t ->  t.getProject().equals(project)
                                    && t.getTaskStatus() != Status.COMPLETE).count();

                            project.setCompleteTaskCounts(completeTaskCounts);
                            project.setUnfinishedTaskCounts(unfinishedTaskCounts);

                            return project;

                        }).collect(Collectors.toList());


        return projectList;
    }
}
