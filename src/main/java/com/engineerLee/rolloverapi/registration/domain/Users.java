package com.engineerLee.rolloverapi.registration.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collation = "users")
public class Users {
    @Id
    private String id;
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
