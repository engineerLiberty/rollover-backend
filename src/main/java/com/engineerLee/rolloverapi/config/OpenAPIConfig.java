package com.engineerLee.rolloverapi.config;

import io.swagger.v3.core.model.ApiDescription;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Liberty odds",
                        url = "https://libertyodds.com"
                ),
                description = "OpenAPI Documentation For Rollover",
                title = "OpenAPI Specification - Liberty-Odds",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8082"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth Description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
//
//      "/v2/api-docs",
//              "/v3/api-docs",
//              "/v3/api-docs/**",
//              "/swagger-resources",
//              "/swagger-resources/**",
//              "/configuration/ui",
//              "/configuration/security",
//              "/swagger-ui/**",
//              "/webjars/**",
//              "/swagger-ui.html"

//    private static final String[] AUTH_WHITE_LIST = {
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/v2/api-docs/**",
//            "/swagger-resources/**"
//    };
}
