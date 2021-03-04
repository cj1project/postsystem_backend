package com.esp.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

    @GetMapping("/home")
    public String homeIndex() {
        return "Admin Security Testing Page";
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
