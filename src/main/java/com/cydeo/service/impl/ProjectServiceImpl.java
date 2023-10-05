package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {


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
}
