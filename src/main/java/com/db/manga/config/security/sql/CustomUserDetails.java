package com.db.manga.config.security.sql;

import com.db.manga.entity.sql.Role;
import com.db.manga.entity.sql.User;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Profile({"sql", "obj"})
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities: " + user.getRoles() );
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleDescription()))
                .collect(Collectors.toList());
    }

    public String getId(){
        return String.valueOf(user.getId());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getSignupDate() {
        return user.getSignupDate();
    }
    public List<Role> getRoles(){
        return user.getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Możesz dodać własną logikę
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Możesz dodać własną logikę
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Możesz dodać własną logikę
    }

    @Override
    public boolean isEnabled() {
        return true; // Możesz dodać własną logikę
    }
}