package com.esp.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

    @GetMapping("/home")
    @PreAuthorize("hasAuthority('admin:read')")
    public String homeIndex() {
        return "Admin Security Testing Page";
    }

    @GetMapping("/userList")
    @PreAuthorize("hasAuthority('admin:read')")
    public String userList() {
        return "Admin --> All Users List Page";
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('admin:read')")
    public String createUser() {
        return "Admin Created User";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('admin:write')")
    public String deleteUser() {
        return "Admin User Deleted!";
    }
}
