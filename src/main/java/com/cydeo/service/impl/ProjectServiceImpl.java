package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
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

    }

    @Override
    public void delete(String code) {

    }
}
