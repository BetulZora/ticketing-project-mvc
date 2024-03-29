package com.cydeo.service.impl;


import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakService keycloakService;
        /*
    ASIDE: projectServiceImpl depends on userServiceImpl  and that in turn depends on projectServiceImpl
    this creates a circular been dependency much like the converter issue.
    use @Lazy in the constructor to prevent this.
     */

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           ProjectService projectService, TaskService taskService, PasswordEncoder passwordEncoder, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
        this.keycloakService = keycloakService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        return userRepository.findAll(Sort.by("firstName")).stream()
                .filter(user->user.getUserName()!=null)
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO userDTO) {

        userDTO.setEnabled(true);

        // during development, we will set enabled to true but this must be activated by user when deployed
        //userDTO.setEnabled(true);
        System.out.println("userDTO.getPassWord() = " + userDTO.getPassWord());

        // want to save encrypted password, not plain password
        User obj = userMapper.convertToEntity(userDTO);
        obj.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
        System.out.println("obj.getPassWord() = " + obj.getPassWord());

        userRepository.save(obj);

        keycloakService.userCreate(userDTO);

    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        // because the UserDTO captured from the UI does not carry the ID field,
        // capture the existing user by the UserName
        // assign the ID to the captured DTO
        // save the DTO, overwriting the existing user -- avoids duplicate entries.
        User user = userRepository.findByUserName(userDTO.getUserName());
        User updatedentity = userMapper.convertToEntity(userDTO);
        updatedentity.setId(user.getId());
        userRepository.save(updatedentity);
        return findByUserName(userDTO.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);

    }

    /**
     * This is a helper method for delete. It will check if a user is available for deleting
     */
    private boolean checkIfUserCanBeDeleted(User user){
        switch (user.getRole().getDescription()){
            case "Manager":
                // check the size of the list of projects. If more than zero, he has unfinished business
                List<ProjectDTO> projectDTOList = projectService.readAllByAssignedManager(user);
                return projectDTOList.size() == 0;

            case "Employee":
                List<TaskDTO> taskDTOList = taskService.readAllByAssignedEmployee(user);
                return taskDTOList.size() == 0;
            default:
                return true;

        }
    }
    @Override
    public void delete(String username) throws TicketingProjectException {
        // use this to avoid permanent deletion from the database vs deleteByUserName
        // retrieve the user we want to interact with
        User user = userRepository.findByUserName(username);

        if(checkIfUserCanBeDeleted(user)){
            // change the isDeleted field to true
            user.setIsDeleted(true);
            // change the username because client wants to reuse usernames in the future
            user.setUserName(user.getUserName() + "-" +user.getId());
            // save the change
            userRepository.save(user);
            keycloakService.delete(username);
        }else {
            throw new TicketingProjectException("User can not be deleted");
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        return userRepository.findByRoleDescriptionIgnoreCase(role).stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
