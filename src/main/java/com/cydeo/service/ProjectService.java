package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService extends CrudService<ProjectDTO, String>{

    public void complete(ProjectDTO projectDTO);

    public List<ProjectDTO> findAllNonCompletedProjects();

}
