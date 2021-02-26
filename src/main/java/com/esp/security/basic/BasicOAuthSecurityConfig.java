package com.esp.security.basic;

import com.esp.security.models.Permission;
import com.esp.security.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    //Configure with Roles
  /*  @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/api/register").permitAll()
                .antMatchers("/", "index", "/css/*", "/js/*", "/user/api/register").permitAll()

                //configuration with hasRole
                .antMatchers("/user/api/**").hasRole(UserRole.USER.name())
                .antMatchers("/admin/api/**").hasRole(UserRole.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/user/api/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name(), UserRole.RESTRICTED_ADMIN.name())
                .antMatchers(HttpMethod.GET,"/admin/api/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.RESTRICTED_ADMIN.name())

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }*/


    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/api/register").permitAll()
                .antMatchers("/", "index", "/css/*", "/js/*", "/user/api/register").permitAll()

                //configuration with hasAuthority (NOTE: remember that the order matters)
                //USER: //Anyone that has USER_READ and USER_WRITE permissions can access this api
                .antMatchers(HttpMethod.GET,"/user/api/**").hasAuthority(Permission.USER_READ.getPermission())
                .antMatchers(HttpMethod.POST,"/user/api/**").hasAuthority(Permission.USER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/user/api/**").hasAuthority(Permission.USER_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/user/api/**").hasAuthority(Permission.USER_WRITE.getPermission())

                //Anyone that has ADMIN_READ permission can access this api but has only view abilities (i.e. HttpMethod.GET in terms of /admin/api/** but can perffom all http operation on /user/api/**)
                //RESTRICTED_ADMIN
                .antMatchers(HttpMethod.GET,"/admin/api/**").hasAuthority(Permission.ADMIN_READ.getPermission())

                //ADMIN
                //Anyone that has ADMIN_WRITE permission can access this api
                .antMatchers(HttpMethod.POST,"/admin/api/**").hasAuthority(Permission.ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/admin/api/**").hasAuthority(Permission.ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/admin/api/**").hasAuthority(Permission.ADMIN_WRITE.getPermission())

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
