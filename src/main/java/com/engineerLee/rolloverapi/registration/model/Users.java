package com.engineerLee.rolloverapi.registration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Users {
    private Long id;
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
