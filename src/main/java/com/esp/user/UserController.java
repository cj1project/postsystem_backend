package com.esp.user;

import com.esp.models.User;
import org.apache.http.client.methods.HttpGet;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

@RestController
@RequestMapping("/api/user")
//@Slf4j
@Validated
public class UserController {

   // private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    /*public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }*/

    @GetMapping("/home")
    public ModelAndView homeIndex(ModelAndView rsp, HttpServletResponse response)
    {
        var s = rsp.addObject("index", "form.html");
       return s;
    }
    @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String loginPage(){
        return "this is the home page for logged in users";
    }

    //@PreAuthorize("")
    @GetMapping(value = "/homePage", produces = MediaType.APPLICATION_JSON_VALUE)
    public String userHome(){
        return "this is the user home after  authentication";
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User register(@RequestBody User reg){
       /* var encodedPassword = passwordEncoder.encode(reg.getPassword());
        reg.setPassword(encodedPassword);*/
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
