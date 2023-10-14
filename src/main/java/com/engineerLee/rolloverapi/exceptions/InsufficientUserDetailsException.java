package com.engineerLee.rolloverapi.exceptions;

public class InsufficientUserDetailsException extends RuntimeException{
    private String message;
    public InsufficientUserDetailsException(String message){
        super(message);
    }
}
