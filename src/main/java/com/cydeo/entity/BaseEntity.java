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
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // use attributes of the @Column annotation to prevent update of the initial creation date.
    @Column(nullable = false, updatable = false)
    protected LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    protected Long insertUserId;
    @Column(nullable = false)
    protected LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    protected Long lastUpdateUserId;

    // use this field for soft delete
    public Boolean isDeleted = false;



    /* PrePersist and PreUpdate have been moved to BaseEntityListener
    // use these methods to set BaseEntity fields
    @PrePersist // This is what to assign on new entries in DB tables
    public void onPrePersist(){
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();

        // these fields will be elaborated later. hardcoded as 1L temporarily
        //this.insertUserId = 1L;
        //this.lastUpdateUserId = 1L;

        // use a listener to get this information
        this.insertUserId = 1L;
        this.lastUpdateUserId = 1L;
    }

    @PreUpdate // This method will run when updating a field.
    public void onPreUpdate(){
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;
    }

     */



}
