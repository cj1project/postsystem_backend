package com.esp.security.dbAuthWithRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder; // Defined the Bean at the PasswordConfigClass

    private final UserDetailsServiceImpl myUserDetailsServiceImpl;

    @Autowired
    public MyWebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsServiceImpl myUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.myUserDetailsServiceImpl = myUserDetailsService;
    }

   /* @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }*/

    //Used to retrieve users from database (but lets use it for example for now
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(myUserDetailsServiceImpl);
        return  provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //Configure with Roles
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/user/api/register", "/admin/api/register").permitAll()
                .antMatchers("/", "index", "/css/*", "/js/*", "/user/api/register").permitAll()

                .anyRequest()
                .authenticated()

                //configuration with hasRole
                /*
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/user/api/**").hasRole("USER_ROLE")
                .antMatchers("/basic").hasRole(UserRole.USER.name())
                .antMatchers("/user/api/**").hasRole(UserRole.USER.name())
                 .antMatchers("/admin/api/**").hasRole(UserRole.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/user/api/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name(), UserRole.RESTRICTED_ADMIN.name())
                .antMatchers(HttpMethod.GET,"/admin/api/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.RESTRICTED_ADMIN.name()) */

                .and()
                .httpBasic();
  }

    // To enable CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:63342", "http://localhost:3000")); // www - obligatory
        //      configuration.setAllowedOrigins(ImmutableList.of("*"));  //set access from all domains
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        //configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type" /*"Cache-Control", "host",*/ ));  // config.setAllowedHeaders(Arrays.asList("header1", "header2")); ("*")
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        //configuration.setExposedHeaders(configuration.getAllowedHeaders());
        configuration.setMaxAge(3600L);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
        }

}
