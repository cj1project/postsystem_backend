package com.esp.security.basic;

import com.esp.security.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class BasicOAuthSecurityConfig extends WebSecurityConfigurerAdapter {


    private PasswordEncoder passwordEncoder;

    @Autowired
    public BasicOAuthSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Drawback is that Basic OAuth does not have logout because every request must have both the username and password
    /*@Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/user/api/**").hasRole(UserRole.USER.name())
                .antMatchers("/admin/api/**").hasRole(UserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    //Used to retrieve users from database (but lets use it for example for now
    protected UserDetailsService userDetailsService() {
        UserDetails userD = User.builder()
                            .username("username")
                            .password(passwordEncoder.encode("password"))
                            .roles(UserRole.USER.name()) //ROLE_USER
                            .build();

        UserDetails userAdmin = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(UserRole.ADMIN.name())  //ROLE_ADMIN
                            .build();

        return new InMemoryUserDetailsManager(userD, userAdmin);
    }

}
