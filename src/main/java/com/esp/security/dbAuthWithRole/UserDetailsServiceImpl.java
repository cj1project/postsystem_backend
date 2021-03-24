package com.esp.security.dbAuthWithRole;

import com.esp.models.Role;
import com.esp.models.User;
import com.esp.user.UserRepositoryCustom;
import com.esp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryCustom repo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    @Autowired
    public UserDetailsServiceImpl(UserRepositoryCustom repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user = repo.getUserByUsername(username);
        //User user = repo.getUserByUserNameAndRole(username);

        User user = service.getUserWithUserNameAndRole(username);  //getUserByUserNameAndRole(username); //

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
       /* var userDetail = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles("USER").build(); */

        /*var userDetailU = new MyUserDetails(user);
        System.out.println("userDetailU: " + userDetailU);
        System.out.println("user role: " + user.getRoles());

        System.out.println("userDetail.getUsername(): " + userDetail.getUsername());
        System.out.println("userDetail.getAuthorities(): " + userDetail.getAuthorities());
        System.out.println("userDetail isEnable: " + userDetail.isEnabled());
        System.out.println("userDetail: " + userDetail);
        System.out.println("(MyUserDetails) userDetail: " + (MyUserDetails) userDetail); */
        return new MyUserDetails(user);
    }

}
