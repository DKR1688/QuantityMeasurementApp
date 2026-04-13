package com.quantity_measurement_app.auth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final String frontendUrl;

	public OAuth2LoginFailureHandler(@Value("${app.frontend.url:http://localhost:3000}") String frontendUrl) {
		this.frontendUrl = frontendUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl)
				.queryParam("error", "Google login failed. Please try again.").build().toUriString();
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
