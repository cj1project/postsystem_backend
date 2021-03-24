/*package com.esp.security.basicAuth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/basicauth" /*"/api/**"*//*)
            .allowCredentials(true)  //setAllowCredentials(true);
			.allowedOrigins("http://localhost:63342")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("Authorization", "Content-type"/*, "header3"*//*)
			//.exposedHeaders("header1", "header2")
			.allowCredentials(false).maxAge(3600);
	}
}*/