package se.yrgo.employeasy.dto;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * class UserDTO
 * abstract User transfer object for exposing username and role to frontend.
 * updated 2022-01-20
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return username.equals(userDTO.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
