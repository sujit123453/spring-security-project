package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.dto.LoginResponse;
import com.bluthinkInc.spring_security_project.dto.customResponse.UserResponseEntity;
import com.bluthinkInc.spring_security_project.model.Users;

public interface UserService {
    UserResponseEntity<Users> register(Users user);

    LoginResponse verify(Users user);
}