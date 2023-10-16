package com.engineerLee.rolloverapi.auth.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String userName;
    private String passWord;
}
