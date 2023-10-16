package com.engineerLee.rolloverapi.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String port_whatsApp_link;
    private String group_whatApp_link;
}
