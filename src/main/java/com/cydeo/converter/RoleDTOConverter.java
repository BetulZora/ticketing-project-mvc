package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDTOConverter implements Converter<String, RoleDTO> {

    RoleService roleService;

    public RoleDTOConverter(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) {

        if(source == null || source.equals("")){
            return null;
        }

        return roleService.findById(Long.parseLong(source));
    }
}
