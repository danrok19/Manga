package com.db.manga.config.security.nosql;

import com.db.manga.entity.nosql.User;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Profile("nosql")
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mapowanie ról na obiekty SimpleGrantedAuthority
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
