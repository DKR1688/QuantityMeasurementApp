package com.quantity_measurement_app.config;

import com.quantity_measurement_app.security.JwtFilter;
import com.quantity_measurement_app.security.OAuth2AuthorizationRequestCookieRepository;
import com.quantity_measurement_app.security.OAuth2FailureHandler;
import com.quantity_measurement_app.security.OAuth2SuccessHandler;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtFilter jwtFilter;
	private final OAuth2SuccessHandler successHandler;
	private final OAuth2FailureHandler failureHandler;
	private final OAuth2AuthorizationRequestCookieRepository authorizationRequestRepository;

	public SecurityConfig(JwtFilter jwtFilter, OAuth2SuccessHandler successHandler, OAuth2FailureHandler failureHandler,
			OAuth2AuthorizationRequestCookieRepository authorizationRequestRepository) {
		this.jwtFilter = jwtFilter;
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		this.authorizationRequestRepository = authorizationRequestRepository;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**", "/oauth2/**", "/login/**")
						.permitAll().requestMatchers("/api/v1/quantities/health").permitAll()
						.requestMatchers("/api/v1/quantities/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/api/v1/measurements/**").hasRole("ADMIN").anyRequest().authenticated())
				.oauth2Login(oauth -> oauth
						.authorizationEndpoint(
								auth -> auth.authorizationRequestRepository(authorizationRequestRepository))
						.successHandler(successHandler).failureHandler(failureHandler))
				.exceptionHandling(ex -> ex.defaultAuthenticationEntryPointFor((request, response, authException) -> {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					response.getWriter().write("{\"success\":false,\"error\":\"Unauthorized\"}");
				}, new AntPathRequestMatcher("/api/**"))
						.defaultAccessDeniedHandlerFor((request, response, accessDeniedException) -> {
							response.setStatus(HttpStatus.FORBIDDEN.value());
							response.setContentType(MediaType.APPLICATION_JSON_VALUE);
							response.getWriter().write("{\"success\":false,\"error\":\"Forbidden\"}");
						}, new AntPathRequestMatcher("/api/**")))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(false);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
