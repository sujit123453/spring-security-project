package com.bluthinkInc.spring_security_project.customExceptions;


public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(String message){
        super(message);
    }
}