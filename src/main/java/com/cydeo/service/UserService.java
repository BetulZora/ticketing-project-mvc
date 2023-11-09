package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService  {

    //Service layers interact with DTOs

    List<UserDTO> listAllUsers();

    UserDTO findByUserName(String username);

    void save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    void deleteByUserName(String username);



}
