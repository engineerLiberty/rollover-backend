package com.engineerLee.rolloverapi.user.controller;

import com.engineerLee.rolloverapi.user.models.AppUser;
import com.engineerLee.rolloverapi.user.response.SearchResponse;
import com.engineerLee.rolloverapi.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
@SecuritySchemes
public class UserController {
    private final UserService registrationService;
    @GetMapping("registeredUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<AppUser> findAll() {
        return registrationService.getAllRegisteredUsers();
    }

    @GetMapping("/findUser")
    @ResponseStatus(HttpStatus.OK)
    public AppUser findUserByEmailOrPhoneNumberOrUsername(@RequestParam(value = "email", required = false) String email,
                                                          @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                          @RequestParam(value = "username", required = false) String username) {
        return registrationService.findUserDetails(email,phoneNumber,username);

    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Hidden
    public List<SearchResponse> search(@RequestParam(value = "firstName", required = false) String firstName,
                                       @RequestParam(value = "lastName", required = false) String lastName,
                                       @RequestParam(value = "email", required = false) String email,
                                       @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                       @RequestParam(value = "username", required = false) String username) {
        return registrationService.search(firstName,lastName,email,phoneNumber,username);

    }
}
