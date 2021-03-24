/*package com.esp.security.dbAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//to get any user from database for authentication purposes
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomUserDetailsRepo userDetailCustomRepo; //implemented with "setValues" repo and "fromDatabase" repo

    @Autowired  //construct with setValues
    public CustomUserDetailsService(@Qualifier("fromDatabase") CustomUserDetailsRepo userDetailCustomRepo) {
        this.userDetailCustomRepo = userDetailCustomRepo;
    }
    @Override // for setValues
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailCustomRepo.selectCustomUserDetail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username))
                );
    }

/*
    private final CustomUserDetailsRepoImplWithDb customUserDetailsRepoImplWithDb; //implemented with real DB

    @Autowired  //construct with real value from DB
    public CustomUserDetailsService(@Qualifier("fromDatabase")CustomUserDetailsRepoImplWithDb customUserDetailsRepoImplWithDb) {
        this.customUserDetailsRepoImplWithDb = customUserDetailsRepoImplWithDb;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var rt = customUserDetailsRepoImplWithDb.loadUserByUsername(username);

        if(rt == null)
            throw new UsernameNotFoundException(String.format("Username %s not found", username));

        return rt;
    } */




//}
