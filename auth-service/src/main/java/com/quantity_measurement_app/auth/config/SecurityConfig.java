package com.quantity_measurement_app.auth.config;

import com.quantity_measurement_app.auth.security.OAuth2LoginFailureHandler;
import com.quantity_measurement_app.auth.security.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String DISABLED_OAUTH_PLACEHOLDER = "disabled";

	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final String googleClientId;
	private final String googleClientSecret;

	public SecurityConfig(OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
			OAuth2LoginFailureHandler oAuth2LoginFailureHandler,
			@Value("${spring.security.oauth2.client.registration.google.client-id:disabled}") String googleClientId,
			@Value("${spring.security.oauth2.client.registration.google.client-secret:disabled}") String googleClientSecret) {
		this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
		this.oAuth2LoginFailureHandler = oAuth2LoginFailureHandler;
		this.googleClientId = googleClientId;
		this.googleClientSecret = googleClientSecret;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/**", "/oauth2/**", "/login/**", "/error").permitAll()
						.anyRequest().authenticated());

		if (isGoogleOauthEnabled()) {
			http.oauth2Login(oauth -> oauth.successHandler(oAuth2LoginSuccessHandler)
					.failureHandler(oAuth2LoginFailureHandler));
		}

		return http.build();
	}

	private boolean isGoogleOauthEnabled() {
		return hasConfiguredValue(googleClientId) && hasConfiguredValue(googleClientSecret);
	}

	private boolean hasConfiguredValue(String value) {
		return value != null && !value.isBlank() && !DISABLED_OAUTH_PLACEHOLDER.equalsIgnoreCase(value);
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
}
