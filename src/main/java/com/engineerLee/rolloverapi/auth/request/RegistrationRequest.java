package com.engineerLee.rolloverapi.auth.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String dob;
    private String phoneNumber;
    private String userName;
    private String email;
    private String passWord;
    private String pin;
    private String address;
    private String state_of_origin;
    private String state_of_residence;
    private String nationality;
}
