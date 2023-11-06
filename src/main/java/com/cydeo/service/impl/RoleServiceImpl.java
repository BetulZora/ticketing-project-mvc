package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// use instead of @Component
public class RoleServiceImpl extends AbstractMapService<RoleDTO,Long> implements CrudService<RoleDTO, Long> {


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

    @Override
    public void update(RoleDTO object) {
        super.update(object.getId(), object);
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
