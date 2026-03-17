package com.quantity_measurement_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// it configure security filter chain, Currently allows all requests for
	// development/testing purposes.
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// it disable CSRF for API development
				.csrf(csrf -> csrf.disable())
				// it allow all HTTP requests for development and testing
				.authorizeHttpRequests(authz -> authz.requestMatchers("/h2-console/**").permitAll()
						.requestMatchers("/api/actuator/**").permitAll().requestMatchers("/api/v1/quantities/**")
						.permitAll().requestMatchers("/api/**").permitAll().anyRequest().permitAll())
				// it enable CORS
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				// it allow H2 console framing
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
