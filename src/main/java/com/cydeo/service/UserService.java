package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudService<UserDTO,String> {


    /* Methods moved to CrudService Parent
    UserDTO save(UserDTO user);
    UserDTO findById(String username);
    List<UserDTO> findAll();
    void delete(UserDTO user);
    void deleteById(String username);
    */






}
