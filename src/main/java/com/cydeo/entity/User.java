package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor @Data
@Entity @Table(name="users")
@Where(clause="is_deleted=false") // this where clause applies to all queries made on this entity, by any repository interface
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
