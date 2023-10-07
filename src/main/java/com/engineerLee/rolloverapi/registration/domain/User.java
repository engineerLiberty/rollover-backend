package com.engineerLee.rolloverapi.registration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode()
@Document(collection = "user")
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String dob;
    @Indexed(unique = true)
    private String phoneNumber;
    @Indexed(unique = true)
    private String userName;
   @Indexed(unique = true)
    private String email;
    private String passWord;
    private String pin;
    private String address;
    private String state_of_origin;
    @Field(name = "state")
    private String state_of_residence;
    @Field(name = "country")
    private String nationality;
}
