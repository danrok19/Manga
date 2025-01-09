package com.db.manga.config.security.objsql;

import com.db.manga.dao.sql.UserRepository;
import com.db.manga.entity.sql.User;
import com.db.manga.service.ObjSqlService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Profile("objsql")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ObjSqlService objSqlService;

    public CustomUserDetailsService(UserRepository userRepository, ObjSqlService objSqlService) {
        this.userRepository = userRepository;
        this.objSqlService = objSqlService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = objSqlService.getUserByUsername(username);
        System.out.println("user: " + user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new CustomUserDetails(user);
    }
}