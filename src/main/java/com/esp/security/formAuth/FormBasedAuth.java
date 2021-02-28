/*package com.esp.security.formAuth;

import com.esp.security.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FormBasedAuth extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public FormBasedAuth(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Override the default

    //Configure with form login: default
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/api/register").permitAll()
                .antMatchers("/", "index", "/css/*", "/js/*", "/user/api/register").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll();
    }


    /*    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/api/register").permitAll()
                .antMatchers("/", "index", "/css/*", "/js/*", "/user/api/register").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
                .loginPage("/login").permitAll
                .defaultSuccessUrl("/dashboard", true)
                .and()
                .rememberMe()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("SESSION")  //"remeber-me"
                    .logoutSuccessUrl("/login")
    }*/

  /*  @Override
    @Bean
    //Used to retrieve users from database (but lets use it for example for now
    protected UserDetailsService userDetailsService() {
        UserDetails userD = User.builder()
                .username("user")
                .password(passwordEncoder.encode("pass"))
                //  .roles(UserRole.USER.name()) //ROLE_USER
                .authorities(UserRole.USER.getGrantedAuthorities())
                .build();

        UserDetails userAdmin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                //  .roles(UserRole.ADMIN.name())  //ROLE_ADMIN
                .authorities(UserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails restrictedAdmin = User.builder()
                .username("intern")
                .password(passwordEncoder.encode("intern"))
                //    .roles(UserRole.RESTRICTED_ADMIN.name())  //ROLE_RESTRICTED_ADMIN
                .authorities(UserRole.RESTRICTED_ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(userD, userAdmin, restrictedAdmin);
    }
}
*/