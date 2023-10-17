package com.engineerLee.rolloverapi.user.models;

import com.engineerLee.rolloverapi.enums.Roles;
import com.engineerLee.rolloverapi.enums.Status;
import com.engineerLee.rolloverapi.token.model.Token;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode()
@Document(collection = "appUser")
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUser implements UserDetails {
    @Id
    private String id;
    @NotBlank
    @Size(max = 80)
    private String fullName;
    private String firstName;
    private String lastName;
    private String otherName;
    private String dob;
    @Indexed(unique = true)
    @NonNull
    private String phoneNumber;
    @Indexed(unique = true)
    @NotBlank
    @Size(max = 20)
    @NonNull
    private String userName;
    @Indexed(unique = true)
    @NotBlank
    @Size(max = 50)
    @Email
    @NonNull
    private String email;
    @NotBlank
    @Size(max = 120)
    @NonNull
    private String passWord;
    private String pin;
    private String address;
    private String state_of_origin;
    @Field(name = "state")
    private String state_of_residence;
    @Field(name = "country")
    private String nationality;
    @Indexed(unique = true)
    private Status status;
    private Roles roles;
    private String whatsappGroupLink;
    @DBRef
    private List<Token> tokens;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.toString()));
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
