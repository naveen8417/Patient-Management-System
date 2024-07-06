package com.naveenj.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.naveenj.service.CustomSuccessHandler;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
	@Autowired
    private DataSource dataSource;
	
	@Autowired
    private CustomSuccessHandler customSuccessHandler;


	
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication()
	            .dataSource(dataSource)
	            .usersByUsernameQuery("SELECT email, password, enabled FROM users WHERE email=?")
	            .authoritiesByUsernameQuery("SELECT email, role FROM user_roles WHERE email=?")
	            .passwordEncoder(passwordEncoder());
	    }
	    
	    

	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  http.csrf(AbstractHttpConfigurer::disable);
		http.
        // other configuration options
        authorizeHttpRequests(authCustomizer -> authCustomizer
            .requestMatchers("/management/**").permitAll()
            .requestMatchers("/disease/**").hasRole("user")
    
        );
		
		http.formLogin(formLogin ->
	    formLogin
	        .loginPage("/patient")
	        .loginProcessingUrl("/patient/login")
	        .permitAll()
	        .successHandler(customSuccessHandler) // Assuming you have a custom success handler
	);

		  return http.build();  
		  }
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	}

