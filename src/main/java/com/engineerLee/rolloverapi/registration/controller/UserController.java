package com.engineerLee.rolloverapi.registration.controller;

import com.engineerLee.rolloverapi.registration.domain.User;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import com.engineerLee.rolloverapi.registration.service.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final RegistrationService registrationService;
    @PostMapping("register")
    @Tag(name = "Register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registration( @RequestBody RegistrationRequest registrationRequest) {
        return registrationService.registerNewUser(registrationRequest);
    }
    @GetMapping("users")
    @Tag(name = "Fetch Registered Users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return registrationService.getAllRegisteredUsers();
    }

    @GetMapping("firstName")
    @Tag(name = "Search By First Name")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getPersonStartWith(@RequestParam("name")String name) {
        return registrationService.getPersonStartWith(name);
    }

    @GetMapping("email")
    @Tag(name = "Search By Email")
    @ResponseStatus(HttpStatus.OK)
    public User getEmail(@RequestParam("email") String email) {
        return registrationService.getEmail(email);
    }

    @GetMapping("phone")
    @Tag(name = "Search By Phone number")
    @ResponseStatus(HttpStatus.OK)
    public User getPhoneNumber(@RequestParam("phone") String phone) {
        return registrationService.getPhone(phone);
    }

    @GetMapping("/findUser")
    @Tag(name = "Get-user-details")
    @ResponseStatus(HttpStatus.OK)
    public User findUserByEmailOrPhoneNumberOrUsername( @RequestParam(value = "email", required = false) String email,
                                                        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                        @RequestParam(value = "username", required = false) String username) {
        return registrationService.findUserDetails(email,phoneNumber,username);

    }

    @GetMapping("/search")
    @Tag(name = "Search")
    @ResponseStatus(HttpStatus.OK)
    public List<User> search(@RequestParam(value = "firstName", required = false) String firstName,
                             @RequestParam(value = "lastName", required = false) String lastName,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                             @RequestParam(value = "username", required = false) String username) {
        return registrationService.search(firstName,lastName,email,phoneNumber,username);

    }
}
