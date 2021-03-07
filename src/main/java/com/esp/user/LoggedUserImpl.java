package com.esp.user;

import com.esp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Component
public class LoggedUserImpl  {

    private UserRepositoryCustom repo;
    private final UserService userService;

    @Autowired
    public LoggedUserImpl(UserService userService) {
        this.userService = userService;
    }

    //@Autowired
    public LoggedUserImpl(UserRepositoryCustom repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    //Maybe I need to have a list of logged in users, which i can access anytime and check fro which user detail

    public User loggedUser(HttpServletRequest request) throws Exception {
        var sessionId =request.getSession().getId();
        String username = null;
        final String authorization = request.getHeader("Authorization");

        if(authorization == null){
            System.out.println("Authorization in Header in LoggedUserImpl is Null");
            throw new Exception("Authorization in Header is Null");
        }

        if(authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);

           /* for(int i = 0; i < values.length; i++){
                System.out.println("values[" + i +"]:"   + values[i]);
            }*/

            username = values[0];
        }
        //System.out.println("username: " + username);
       // User user = repo.getUserByUsername(username);
       User user = userService.getUserWithUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("No User with the given username was found");
        }

        //check if the user sessionId = the sessionId   //make sessionId in user class transient.

        //if it passes the above check

        return user;
    }


    public User loggedUserWithSpring(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        System.out.println("username: " + username);
       return userService.getUserWithUsername(username);
    }

    public UserRepositoryCustom getRepo() {
        return repo;
    }

    public void setRepo(UserRepositoryCustom repo) {
        this.repo = repo;
    }

    public UserService getUserService() {
        return userService;
    }

}
