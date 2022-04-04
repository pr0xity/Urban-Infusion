package no.ntnu.appdevapi.security;


import no.ntnu.appdevapi.entities.PermissionLevel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import no.ntnu.appdevapi.entities.User;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Contains authentication information, needed by UserDetailsService
 */
public class AccessUserDetails implements UserDetails{

    private final String username;
    private final String password;
    private final boolean isActive;


    public AccessUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isActive = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
