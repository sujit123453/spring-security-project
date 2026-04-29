package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.model.Users;
import com.bluthinkInc.spring_security_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo repo;
    public MyUserDetailsService(UserRepo repo){
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
