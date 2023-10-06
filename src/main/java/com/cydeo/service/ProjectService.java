package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;

import java.util.List;

public interface ProjectService extends CrudService<ProjectDTO, String>{

    public void complete(ProjectDTO projectDTO);

    public List<ProjectDTO> findAllNonCompletedProjects();

    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);

}
