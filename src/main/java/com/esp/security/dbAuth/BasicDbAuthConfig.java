/*package com.esp.security.dbAuth;

import com.esp.security.dbAuthWithRole.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicDbAuthConfig extends WebSecurityConfigurerAdapter{

        @Autowired
        private PasswordEncoder passwordEncoder;

        public BasicDbAuthConfig(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

  */      //Drawback of Basic OAuth is that Basic OAuth does not have logout because every request must have both the username and password

        //Configure with Roles
 /*       @Override
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
                    .httpBasic()
                    .and()
                    .rememberMe()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("SESSION")  //"remeber-me"
                    .logoutSuccessUrl("/logout");

        }

        // To enable CORS
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:63342")); // www - obligatory
            //      configuration.setAllowedOrigins(ImmutableList.of("*"));  //set access from all domains
            configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
            configuration.setAllowCredentials(true);
            //configuration.addAllowedOrigin("http://localhost:63342");
            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type" /*"Cache-Control", "host",*/ /* ));

            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            return source;
        }

        //UserDetails with live Db

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
            auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Autowired
    private UserDetailsServiceImpl customUserDetailsRepoImplWithDb;

    //Used to retrieve users from database (but lets use it for example for now
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder);
            provider.setUserDetailsService(customUserDetailsRepoImplWithDb);
            return  provider;
    }


}*/