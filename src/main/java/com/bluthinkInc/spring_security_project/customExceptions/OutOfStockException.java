package com.bluthinkInc.spring_security_project.customExceptions;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message){
        super(message);
    }
}
