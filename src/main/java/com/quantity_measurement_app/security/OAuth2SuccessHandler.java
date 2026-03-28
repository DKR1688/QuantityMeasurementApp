package com.quantity_measurement_app.security;

import com.quantity_measurement_app.model.User;
import com.quantity_measurement_app.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final OAuth2AuthorizationRequestCookieRepository authorizationRequestRepository;
	private final String redirectUri;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public OAuth2SuccessHandler(UserRepository userRepository, JwtUtil jwtUtil,
			OAuth2AuthorizationRequestCookieRepository authorizationRequestRepository,
			@Value("${app.oauth2.redirect-uri:http://localhost:3000}") String redirectUri) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.authorizationRequestRepository = authorizationRequestRepository;
		this.redirectUri = redirectUri;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
		String email = oauthUser.getAttribute("email");

		if (email == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not found in Google account");
			return;
		}

		User user = userRepository.findByEmail(email).orElseGet(() -> {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
			newUser.setRole("ROLE_USER");
			newUser.setProvider("GOOGLE");
			return userRepository.save(newUser);
		});

		String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
		authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
		response.sendRedirect(redirectUri + "?token=" + token);
	}
}
