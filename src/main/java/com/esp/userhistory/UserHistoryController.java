package com.esp.userhistory;

import com.esp.models.User;
import com.esp.models.UserHistory;
import com.esp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class UserHistoryController {

    @Autowired
    private UserHistoryService service;
    @Autowired
    private UserService userService;

    @GetMapping(value="/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public String HomeH(){
        return "History Home";
    }

    @GetMapping(value = "/get-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserHistory> history(){
       var userHistory = userService.getUser("");
        return service.getAllHistory(userHistory);
    }

    @GetMapping(value = "/user-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> userHistory(){
        return service.getAllUserHistoryOfAUser();
    }
}
