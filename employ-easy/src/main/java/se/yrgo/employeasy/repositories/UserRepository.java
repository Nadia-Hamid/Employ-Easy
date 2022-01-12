package se.yrgo.employeasy.repositories;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class UserRepository implements UserDetails {

    public UserRepository() {}

    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if(authentication != null) {
            return Collections.unmodifiableCollection(authentication.getAuthorities());
        } else {
            return null;
        }
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("Not needed yet");
    }

    @Override
    public String getUsername() {
        if(authentication != null){
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException("Not needed yet");
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException("Not needed yet");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException("Not needed yet");
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException("Not needed yet");
    }
}
