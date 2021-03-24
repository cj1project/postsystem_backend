package com.esp.user;

import com.esp.models.ImageEntity;
import com.esp.models.Role;
import com.esp.models.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/user/api")
public class UserController {
    public static String LOGGED_USER_ID;

   private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @GetMapping("/home")
   // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public String homeIndex( )
    {
       return "User Home";
    }

    @PostMapping(value="/login")
    public User loginPage(HttpServletRequest request) throws Exception {  //returns logged in user
        var loggedUser = new LoggedUserImpl(service);
        var user = loggedUser.loggedUser(request);  //loggedUser.loggedUserWithSpring(); //if it passed
        LOGGED_USER_ID = user.getId();
       // return "this is the home page for logged in users";
        return user;
    }

    @GetMapping(value="/logout")
    public String logoutPage(HttpServletRequest request) throws Exception {
        var loggedUser = new LoggedUserImpl(service);
        //request.getHeader("Authorization");
        request.setAttribute("header", null);
        LOGGED_USER_ID = null;
        var user = loggedUser.loggedUser(request);  //loggedUser.loggedUserWithSpring(); //if it passed
        return "user logged out";
    }

    //@PreAuthorize("")
    @GetMapping(value = "/homePage", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("")//@PreAuthorize("hasRole('ROLE_USER')")   //@RolesAllowed("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public String userHome(HttpServletRequest request) throws NotFoundException {
        if(LOGGED_USER_ID == null){
            System.out.println("user is not logged in");
            throw new NotFoundException("User is not Logged");
        }
        return "this is the user home after  authentication";
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody User user) throws FileNotFoundException {
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
    public String deRegister(HttpServletRequest request) throws Exception {
        var loggedUser = new LoggedUserImpl(service);
        var user = loggedUser.loggedUser(request);  //loggedUser.loggedUserWithSpring(); //if it passed
        LOGGED_USER_ID = user.getId();
        String name = user.getFirstname();
        service.deregister(user.getId());
        return name + ", You have successfully deRegistered";
    }

    @PostMapping(value="/createEmptyUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public User createUser() {
        return service.createEmptyUser();
    }

    @GetMapping(value = "/get-user/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('ROLE_USER')")  //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_RESTRICTED_ADMIN', 'ROLE_ADMIN')")
    public User getUser(@PathVariable String id)throws IOException, NotFoundException {
        if(UserController.LOGGED_USER_ID == null){
            System.out.println("user is not logged in");
            throw new NotFoundException("User is not Logged");
        }

       return service.getUser(id);
    }

    @GetMapping(value = "/getUserData/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserDate(@PathVariable String userId){
        return service.userData(userId);
    }

    //get all pics of a particular user
    @GetMapping(value = "/get-pics/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageEntity> getPics(@PathVariable String user_id) throws IOException, NotFoundException {
        if(UserController.LOGGED_USER_ID == null){
           System.out.println("user is not logged in");
            throw new NotFoundException("User is not Logged");
        }
        return service.getUsersPics(user_id);
    }
    @GetMapping(value = "/delete-pic-from-db/{image_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deletePic(@PathVariable String image_id) throws IOException, NotFoundException {
            if(UserController.LOGGED_USER_ID == null){
                 System.out.println("user is not logged in");
                 throw new NotFoundException("User is not Logged");
             }
        return service.deleteImage(image_id);
    }

}
