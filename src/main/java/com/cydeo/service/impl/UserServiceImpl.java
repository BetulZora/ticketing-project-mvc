package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

        userRepository.save(userMapper.convertToEntity(userDTO));

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

    @Override
    public void delete(String username) {
        // use this to avoid permanent deletion from the database vs deleteByUserName
        // retrieve the user we want to interact with
        User user = userRepository.findByUserName(username);
        // change the isDeleted field to true
        user.setIsDeleted(true);
        // save the change
        userRepository.save(user);



    }
}
