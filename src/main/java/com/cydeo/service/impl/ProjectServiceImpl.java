package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;
    /*
    ASIDE: projectServiceImpl depends on userServiceImpl  and that in turn depends on projectServiceImpl
    this creates a circular been dependency much like the converter issue.
    use @Lazy in the constructor to prevent this.
     */

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper,
                              @Lazy UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {

        return projectMapper.convertToDTO(projectRepository.findByProjectCode(code));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        return projectRepository.findAll().stream()
                //.filter(p->p.getProjectCode()!=null)
                .map(projectMapper::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        Project entity = projectMapper.convertToEntity(dto);
        projectRepository.save(entity);

    }

    @Override
    public void update(ProjectDTO dto) {
        // Find current version of project
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());
        // convert the dto to entity and assign id to entity
        Project updatedProject = projectMapper.convertToEntity(dto);
        updatedProject.setId(project.getId());
        // we also need to get the status as well
        updatedProject.setProjectStatus(project.getProjectStatus());
        // save
        projectRepository.save(updatedProject);

    }

    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);

        project.setIsDeleted(true);
        // to free up the project code for future project, modify the project code
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        projectRepository.save(project);

        // the tasks associated with the project must also be deleted

        taskService.deleteByProject(projectMapper.convertToDTO(project));
    }

    @Override
    public void complete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setProjectStatus(Status.COMPLETE);

        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDTO(project));

    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        // used a hardcoded username initially
        //UserDTO currentUserDTO = userService.findByUserName("mike@gmail.com");

        // to capture manager from security use SecurityContextHolder
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount details = (SimpleKeycloakAccount) authentication.getDetails();
        String username = details.getKeycloakSecurityContext().getToken().getPreferredUsername();

        UserDTO currentUserDTO = userService.findByUserName(username);

        User user = userMapper.convertToEntity(currentUserDTO);
        // projects will be listed by manager assigned
        List<Project> list = projectRepository.findAllByAssignedManager(user);
        return list.stream().map((project -> {
            ProjectDTO obj = projectMapper.convertToDTO(project);
            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTasks(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTasks(project.getProjectCode()));
            return obj;
        })).collect(Collectors.toList());

    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User assignedManager) {
        List<Project> list = projectRepository.findAllByAssignedManager(assignedManager);
        return list.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }
}
