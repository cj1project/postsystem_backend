package com.esp.user;

import com.esp.models.Role;
import com.esp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user/api")
public class UserController {

   private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    //hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission'), @PreAuthorize("hasAuthority('user:write')"

    @GetMapping("/home")
   // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public String homeIndex( )
    {
       return "User Home";
    }
    @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String loginPage(){
        return "this is the home page for logged in users";
    }

    //@PreAuthorize("")
    @GetMapping(value = "/homePage", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('ROLE_USER_ROLE')")   //@RolesAllowed("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public String userHome(){
        return "this is the user home after  authentication";
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public long register(@RequestBody User user){
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        Role role = new Role();

        role.setId(user.getId());
        role.setName("USER");

        roles.add(role);

        user.setRoles(roles);

        return service.registerNewUser(user);
    }

    @PostMapping(value = "/deregister", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasAuthority('user:write')")
    public String deRegister(@RequestBody User user){
        return user.getFirstname() + ", You have successfully deRegistered";
    }

    @PostMapping(value="/createEmptyUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public User createUser() {
        return service.createEmptyUser();
    }

    @GetMapping(value = "/get-user/{id}", produces = "application/json")
    //@PreAuthorize("hasRole('ROLE_USER')")  //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public User getUser(@PathVariable long id){
       return service.getUser(id);
    }

    @GetMapping(value = "/getUserData/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserDate(@PathVariable long userId){
        return service.userData(userId);
    }

}
