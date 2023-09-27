package com.cydeo.service;


import com.cydeo.dto.RoleDTO;

import java.util.List;

// we are abstracting this. Good practice to separate Interface and Implementation
public interface RoleService extends CrudService<RoleDTO,Long>{
    // save, retrieve(findById or findAll) , update, delete


    /* Methods moved to CrudService Parent
    RoleDTO save(RoleDTO role);
    RoleDTO findByID(Long id);
    List<RoleDTO> findAll();
    void delete(RoleDTO role);
    void deleteById(Long id);
*/

   // Can have aditional RoleService specific business logic not inherited from CrudService

}
