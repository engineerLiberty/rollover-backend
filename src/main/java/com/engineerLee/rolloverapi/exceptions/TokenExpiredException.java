package com.engineerLee.rolloverapi.exceptions;

public class TokenExpiredException extends RuntimeException{
    private String msg;
    public TokenExpiredException(String msg){
        super(msg);
    }
}
