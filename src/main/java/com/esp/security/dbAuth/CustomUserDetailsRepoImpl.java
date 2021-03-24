/*package com.esp.security.dbAuth;

import com.esp.security.models.Permission;
import com.esp.security.models.UserRole;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Implementation of CustomUserDetailsRepo with setValues and not with a real database
@Repository("setValues")
public class CustomUserDetailsRepoImpl implements CustomUserDetailsRepo { */ /*

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsRepoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<CustomUserDetails> selectCustomUserDetail(String username) {
        return getCustomUserDetailsUsers()
                .stream()
                .filter(userDetail -> username.equals(userDetail.getUsername()))
                .findFirst();
    }

    //set values for test purposes
    private List<CustomUserDetails> getCustomUserDetailsUsers(){
        List<CustomUserDetails> userDetails = Lists.newArrayList(
            new CustomUserDetails(
                    "user",
                    passwordEncoder.encode("pass"),
                    UserRole.USER.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            ),
                new CustomUserDetails(
                        "intern",
                        passwordEncoder.encode("intern"),
                        UserRole.RESTRICTED_ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new CustomUserDetails(
                        "admin",
                        passwordEncoder.encode("admin"),
                        UserRole.ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
        return userDetails;
    }
} */
