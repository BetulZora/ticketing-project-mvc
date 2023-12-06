package com.cydeo.entity.common;

import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserPrincipal implements UserDetails {
    @Getter
    private final Long id;
    private final String username;
    private final String password;
    private final boolean isEnabled;
    private final Role role;
    private final Set<GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.password = user.getPassWord();
        this.isEnabled = user.isEnabled();
        this.role = user.getRole();
        this.authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getDescription()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(this.role.getDescription());

        authorityList.add(authority);

        return authorityList;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getUsername() {

        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled();
    }

    public Long getId(){
        return this.getId();
    }
}
