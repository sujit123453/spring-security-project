package com.bluthinkInc.spring_security_project.service.impl;

import com.bluthinkInc.spring_security_project.model.Users;
import com.bluthinkInc.spring_security_project.repo.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo repo;
    public MyUserDetailsServiceImpl(UserRepo repo){
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        String role = user.getRole();

        if (role == null || role.isEmpty()) {
            role = "USER";
        }

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}
