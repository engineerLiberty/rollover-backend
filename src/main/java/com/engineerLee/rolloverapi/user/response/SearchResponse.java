package com.engineerLee.rolloverapi.user.response;

import com.engineerLee.rolloverapi.enums.Roles;
import com.engineerLee.rolloverapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResponse {
    private String fullName;
    private String firstName;
    private String lastName;
    private String otherName;
    private String dob;
    private String phoneNumber;
    private String userName;
    private String email;
    private String address;
    private String state_of_origin;
    private String state_of_residence;
    private String nationality;
    private Status status;
    private Roles roles;
}
