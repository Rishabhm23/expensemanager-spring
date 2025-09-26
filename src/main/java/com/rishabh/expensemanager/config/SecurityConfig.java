package com.rishabh.expensemanager.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rishabh.expensemanager.security.JwtRequestFilter;
import com.rishabh.expensemanager.service.AppUserDetailsService;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Bean
	public SecurityFilterChain securityFilterChild(HttpSecurity httpSecurity) throws Exception {
		
		
		httpSecurity
	    .cors(Customizer.withDefaults()) // enable CORS with default settings
	    .csrf(AbstractHttpConfigurer::disable) // disable CSRF
	    .authorizeHttpRequests(auth -> auth
	        .requestMatchers("/status", "/health", "/register", "/activate", "/login")
	        .permitAll()
	        .anyRequest()
	        .authenticated()
	        )
	        .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	        
	
	
	return httpSecurity.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-type", "Accept"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider  authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(appUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authenticationProvider);
		
	} 

	
}
