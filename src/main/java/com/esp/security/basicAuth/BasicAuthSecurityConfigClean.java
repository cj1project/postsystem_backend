package com.esp.security.basicAuth;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Used to tell config class that we want to use Annotation based authentication on method level (controller class)
public class BasicAuthSecurityConfigClean extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public BasicAuthSecurityConfigClean(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Drawback of Basic OAuth is that Basic OAuth does not have logout because every request must have both the username and password

    //Configure with Roles
   @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                //.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/user/api/register").permitAll()
                .antMatchers("/", "index", "/css/*", "/js/*", "/user/api/register").permitAll()

                //configuration with hasRole
               //.antMatchers("/basic").hasRole(UserRole.USER.name())
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();

       // REST is stateless
      /* http.sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
    }


    // To enable CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:63342")); // www - obligatory
    //      configuration.setAllowedOrigins(ImmutableList.of("*"));  //set access from all domains
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        //configuration.addAllowedOrigin("http://localhost:63342");
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type" /*"Cache-Control", "host",*/ ));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    //Used to retrieve users from database (but lets use it for example for now
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
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

        return new InMemoryUserDetailsManager(user, userAdmin, restrictedAdmin);
    }
}

