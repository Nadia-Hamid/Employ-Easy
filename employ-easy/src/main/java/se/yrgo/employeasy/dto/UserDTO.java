package se.yrgo.employeasy.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class UserDTO implements Serializable {

    private final String username;
    private final Collection<GrantedAuthority> authorities;

    public UserDTO(String username, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }
}
