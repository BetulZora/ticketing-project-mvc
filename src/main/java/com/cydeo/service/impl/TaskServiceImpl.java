package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper,
                           ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public TaskDTO findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            return taskMapper.convertToDto(task.get());
        }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {

        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate( LocalDate.now());
        Task task = taskMapper.convertToEntity(dto);
        taskRepository.save(task);

    }

    @Override
    public void update(TaskDTO dto) {
        Optional<Task> task = taskRepository.findById(dto.getId());
        Task convertedTask = taskMapper.convertToEntity(dto);

        if(task.isPresent()){
            convertedTask.setId(task.get().getId());
            // using ternary. If dto status is null, this is an update from the form and we will use use the status in the DB
            // if dto is not null, it means we are saving a change in the task status (ex. completing the task)
            convertedTask.setTaskStatus(dto.getTaskStatus()== null ? task.get().getTaskStatus(): dto.getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        if(foundTask.isPresent()){
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }


    }

    @Override
    public Integer totalNonCompletedTasks(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public Integer totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO project) {
        List<TaskDTO> list = listAllByProject(project);
        list.forEach(taskDTO -> delete(taskDTO.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO project) {
        List<TaskDTO> list = listAllByProject(project);
        list.forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    private List<TaskDTO> listAllByProject(ProjectDTO project) {
        List<Task> list = taskRepository.findAllByProject(projectMapper.convertToEntity(project));
        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }
}
