package com.db.manga.config.security.nosql;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Profile("nosql")
public class CustomUserDetailsManager implements UserDetailsManager {

    private final CustomUserDetailsService customUserDetailsService;

    public CustomUserDetailsManager(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void createUser(UserDetails user) {
        // You would need to save the user in your MongoDB database
        // For example: userRepository.save(new User(...));
    }

    @Override
    public void updateUser(UserDetails user) {
        // Update the user in your MongoDB database
    }

    @Override
    public void deleteUser(String username) {
        // Delete the user from your MongoDB database
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // Implement password change logic here
    }

    @Override
    public boolean userExists(String username) {
        try {
            customUserDetailsService.loadUserByUsername(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserDetailsService.loadUserByUsername(username);
    }
}
