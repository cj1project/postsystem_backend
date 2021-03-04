package com.esp.security.basicAuth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
public class BasicAuthenticationController {

    //@CrossOrigin(origins={ "http://localhost:63342", "http://localhost:3000", "http://localhost:4200" })
    @GetMapping(value = "/basic")
    //@PreAuthorize("hasAuthority('user:read')")
    public AuthenticationBean authenticate() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return new AuthenticationBean("You are authenticated");
    }

    @GetMapping(value = "/logout")
    //@PreAuthorize("hasAuthority('user:read')")
    public AuthenticationBean logoutFunc() {
        return new AuthenticationBean("You are Logged out, redirected to login page");
    }

    @GetMapping(value = "/logged_out")
    public AuthenticationBean loggedOutFunc() {
        return new AuthenticationBean("You are Logged out to logged out route");
    }
}