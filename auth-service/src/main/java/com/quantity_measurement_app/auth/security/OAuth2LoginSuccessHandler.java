package com.quantity_measurement_app.auth.security;

import com.quantity_measurement_app.auth.model.User;
import com.quantity_measurement_app.auth.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final String frontendUrl;

	public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtUtil jwtUtil,
			@Value("${app.frontend.url:http://localhost:4200}") String frontendUrl) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.frontendUrl = frontendUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		String email = oauth2User.getAttribute("email");

		if (email == null || email.isBlank()) {
			sendRedirectWithError(request, response, "Google account did not provide an email address.");
			return;
		}

		User user = userRepository.findByEmail(email).orElseGet(() -> createGoogleUser(email));
		String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
		String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl).queryParam("token", token).build()
				.toUriString();

		clearAuthenticationAttributes(request);
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	private User createGoogleUser(String email) {
		User user = new User();
		user.setEmail(email);
		user.setRole("ROLE_USER");
		user.setProvider("GOOGLE");
		return userRepository.save(user);
	}

	private void sendRedirectWithError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws IOException {
		String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl).queryParam("error", errorMessage).build()
				.toUriString();
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
