/*package com.esp.security.dbAuth;

import com.esp.models.User;
import com.esp.security.models.Permission;
import com.esp.security.models.UserRole;
import com.esp.user.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Implementation of CustomUserDetailsRepo with a real database
@Repository("fromDatabase")
public class CustomUserDetailsRepoImplWithDb implements CustomUserDetailsRepo { */ /*

    private UserService service;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsRepoImplWithDb(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    //from DB
   // @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = service.getUserWithUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Username is not found");
        }

        return  new CustomUserDetails(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                UserRole.USER.getGrantedAuthorities(),
                true,
                true,
                true,
                true
        );
    }

    private List<CustomUserDetails> getListOfCustomUserDetails(){
        List<CustomUserDetails>userDetailsList = new ArrayList<>();

        List<User> userList = new ArrayList<User>();
        userList = service.getUserList();

        for (User user : userList) {
            userDetailsList.add(
                    new CustomUserDetails(
                            user.getUsername(),
                            passwordEncoder.encode(user.getPassword()),
                            null, //Not correct
                            true,
                            true,
                            true,
                            true
                    )
            );
        }

        return userDetailsList;
    }

    @Override
    public Optional<CustomUserDetails> selectCustomUserDetail(String username) {
        var usernameFound = loadUserByUsername(username);
        if(usernameFound == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return getListOfCustomUserDetails()
                .stream()
                .filter(userDetail -> usernameFound.getUsername().equals(userDetail.getUsername()))
                .findFirst();
    }
} */
