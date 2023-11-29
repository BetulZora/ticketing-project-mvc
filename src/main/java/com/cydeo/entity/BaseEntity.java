package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // use attributes of the @Column annotation to prevent update of the initial creation date.
    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;

    // use this field for soft delete
    private Boolean isDeleted = false;

    // use these methods to set BaseEntity fields
    @PrePersist // This is what to assign on new entries in DB tables
    public void onPrePersist(){
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();

        // these fields will be elaborated later.
        this.insertUserId = 1L;
        this.lastUpdateUserId = 1L;
    }

    @PreUpdate // This method will run when updating a field.
    public void onPreUpdate(){
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;
    }



}
