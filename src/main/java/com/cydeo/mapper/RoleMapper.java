package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    // inject model mapper
    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // convert DTO to entity
    public Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto, Role.class);
    }

    // convert entity to DTO
    public RoleDTO convertToDto(Role entity){
        return modelMapper.map(entity, RoleDTO.class);
    }
}
