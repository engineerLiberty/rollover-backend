package com.engineerLee.rolloverapi.auth.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private String name;
    private String userName;
    private String personal_whatsApp_group_link;
    private String whatApp_group_link;
}
