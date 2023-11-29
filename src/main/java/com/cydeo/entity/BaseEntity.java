package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime insertDateTime;
    private Long insertUserId;
    private LocalDateTime lastUpdateDateTime;
    private Long lastUpdateUserId;

    // use this field for soft delete
    private Boolean isDeleted = false;

    // use this to set BaseEnetity fields
    public void onPrePersist(){
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();

        // these fields will be elaborated later.
        this.insertUserId = 1L;
        this.lastUpdateUserId = 1L;
    }



}
