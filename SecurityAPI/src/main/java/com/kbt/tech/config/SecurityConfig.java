package com.kbt.tech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  
		http.authorizeRequests(authorize -> authorize.regexMatchers(HttpMethod.GET,"/kbt/home").permitAll().anyRequest().authenticated());		
    	http.formLogin();
     	http.httpBasic();
     
		
//		http.csrf()
//		    .disable()
//		    .cors()
//		    .and()
//		    .exceptionHandling()
//		    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//		    .and()
//		    .sessionManagement()
//		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		    .and()
//		    .authorizeRequests(
//		    		authorize -> authorize
//		    		         .regexMatchers(HttpMethod.GET,"/home").permitAll()
//		    		         .regexMatchers(HttpMethod.GET,"/employees").authenticated()
//		    		         .anyRequest().authenticated());
		
//		    
//		http.formLogin();
//     	http.httpBasic();
		    
		return http.build();
		
	}
	


}
