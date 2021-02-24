package com.esp.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/admin/api")
public class AdminController {

    @GetMapping("/home")
    public String homeIndex() {
        return "Admin Security Testing Page";
    }
}
