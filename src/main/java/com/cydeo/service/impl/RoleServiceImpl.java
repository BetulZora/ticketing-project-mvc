package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {
    /*
    Updated this page so that it uses the generic mapper MapperUtil instead of RoleMapper
    Old code has been commented out
     */

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper; // although injected, will not use this bean
    private final MapperUtil mapperUtil;


    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        // invoke the repository to return a list of roles
        // need to convert list of DTO to list of entity using Mappers
        return roleRepository.findAll().stream()
                .filter(m->m.getDescription()!=null)
               // .map(roleMapper::convertToDto) // commented out. Preferring to use the generic mapper
                .map(m -> mapperUtil.convert(m, new RoleDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {

       // return roleMapper.convertToDto(roleRepository.findById(id).get());
        return mapperUtil.convert(roleRepository.findById(id),new RoleDTO());
    }
}
