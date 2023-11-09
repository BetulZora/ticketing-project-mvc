package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        // invoke the repository to return a list of roles
        // need to convert list of DTO to list of entity using Mappers
        return roleRepository.findAll().stream()
                .map(roleMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {

        return roleMapper.convertToDto(roleRepository.getById(id));
    }
}
