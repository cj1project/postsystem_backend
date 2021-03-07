package com.esp.admin;

import com.esp.models.Role;
import com.esp.models.User;
import com.esp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    public AdminController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/home")
    public String homeIndex() {
        return "Admin Security Testing Page";
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody User admin) throws FileNotFoundException {
        var encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        Role role = new Role();

        role.setId(admin.getId());
        role.setName("ADMIN");

        roles.add(role);

        admin.setRoles(roles);

        return service.registerNewUser(admin);
    }

    @GetMapping("/userList")
    public String userList() {
        return "Admin --> All Users List Page";
    }

    @PostMapping("/createUser")
    public String createUser() {
        return "Admin Created User";
    }

    @DeleteMapping("/delete")
    //@PreAuthorize("hasAuthority('admin:write')")
    public String deleteUser() {
        return "Admin User Deleted!";
    }
}
