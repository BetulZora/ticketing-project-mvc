package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// use instead of @Component
public class RoleServiceImpl extends AbstractMapService<RoleDTO,Long> implements RoleService {


    @Override
    public RoleDTO save(RoleDTO object) {

        return super.save(object.getId(), object);
    }

    @Override
    public List<RoleDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);

    }

    /*
    Removed due to limited use-case
    @Override
    public void delete(RoleDTO object) {
        super.delete(object);

    }

     */

    @Override
    public RoleDTO findById(Long id) {
        return super.findById(id);
    }
}
