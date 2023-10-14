package com.engineerLee.rolloverapi.registration.controller;

import com.engineerLee.rolloverapi.registration.models.User;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import com.engineerLee.rolloverapi.registration.service.RegistrationService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
@SecuritySchemes
public class UserController {
    private final RegistrationService registrationService;
    @GetMapping("registeredUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return registrationService.getAllRegisteredUsers();
    }

    @GetMapping("/findUser")
    @ResponseStatus(HttpStatus.OK)
    public User findUserByEmailOrPhoneNumberOrUsername( @RequestParam(value = "email", required = false) String email,
                                                        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                        @RequestParam(value = "username", required = false) String username) {
        return registrationService.findUserDetails(email,phoneNumber,username);

    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Hidden
    public List<User> search(@RequestParam(value = "firstName", required = false) String firstName,
                             @RequestParam(value = "lastName", required = false) String lastName,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                             @RequestParam(value = "username", required = false) String username) {
        return registrationService.search(firstName,lastName,email,phoneNumber,username);

    }
}
