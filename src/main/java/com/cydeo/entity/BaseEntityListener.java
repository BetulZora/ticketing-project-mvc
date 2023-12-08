package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class BaseEntityListener extends AuditingEntityListener {


    // PrePersist and PreUpdate have been migrated from BaseEntity and modified appropriately

    @PrePersist// This is what to assign on new entries in DB tables
    public void onPrePersist(BaseEntity baseEntity) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.insertDateTime = LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();

        // these fields will be elaborated later. hardcoded as 1L temporarily
        //this.insertUserId = 1L;
        //this.lastUpdateUserId = 1L;

        // use a listener to get this information previously hardcoded insterUserId and lasUpdateUserId fields
        // gate it with an if condition
        if(authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();
            baseEntity.insertUserId = ((UserPrincipal) principal).getId();
            baseEntity.lastUpdateUserId =((UserPrincipal) principal).getId();
        }
    }

    @PreUpdate// This method will run when updating a field.
    public void onPreUpdate(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.lastUpdateDateTime = LocalDateTime.now();

        // Gate the last UpdatedUserId by a condition based on authentication
        //baseEntity.lastUpdateUserId = 1L;
        if(authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();
            baseEntity.lastUpdateUserId =((UserPrincipal) principal).getId();
        }

    }
}
