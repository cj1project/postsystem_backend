package com.esp.user;

import com.esp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User register(@RequestBody User reg){
        return service.registerNewUser(reg);
    }

    @PostMapping(value = "/deregister", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deRegister(@RequestBody User user){
        return user.getFirstname() + ", You have successfully deRegistered";
    }

    @PostMapping(name="/createUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public User createUser() {
        return service.createUser();
    }

    @GetMapping(value = "/get-user/{id}", produces = "application/json")
    public User getUser(@PathVariable long id){
       return service.getUser(id);
    }

}
